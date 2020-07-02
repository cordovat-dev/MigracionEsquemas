package imp.conectores;

import imp.items.ItemDisableConstraints;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConectorDisableConstraints extends ConectorAbstracto {

	public ConectorDisableConstraints(Connection con_){
		super();
		this.con = con_;
		this.nombre = "DISABLE_CONSTRAINTS";			
	}
	
	@Override
	protected void cargarLista() throws SQLException {
		this.sql = this.armarSQL();
		this.sql +=
		"\norder by \n"+
	    "   decode(c.constraint_type,'P',1,'R',0,'U',2,'C',3,4), \n"+ 
	    "   c.owner, \n"+
	    "   c.table_name, \n"+ 
	    "   c.constraint_name \n";	

		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemDisableConstraints tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = new ItemDisableConstraints();
			tbs.setOwner(rs.getString("owner"));
			tbs.setNombreTabla(rs.getString("table_name"));
			tbs.setNombre(rs.getString("constraint_name"));
			tbs.setTipo(rs.getString("constraint_type"));
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
	}

	protected String armarSQL(){
		String sql =
		"select \n"+ 
		"   decode(constraint_type,'P','pk','R','fk','U','uk','C','ck',constraint_type) constraint_type,\n"+ 
		"   c.owner, \n"+
		"   c.table_name, c.constraint_name \n"+ 
		"from dba_constraints c \n"+
		"where \n" +
		"	status = 'ENABLED' and \n" +
		"	c.constraint_name not like 'BIN$%' and \n"+
		"	c.table_name not like 'BIN$%' and \n"+
		"	c.constraint_type not in ('V','O') \n";
		
		if (!this.itemsExcluir.isEmpty()){
			sql+="	and c.owner not in " + getCadenaItemsSQL(this.itemsExcluir);
		}
		
		if (!this.itemsIncluir.isEmpty()){
			sql+="	and c.owner in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		return sql;				
	} 	
}
