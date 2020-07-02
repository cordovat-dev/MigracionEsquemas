package imp.conectores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import imp.items.ItemEnableConstraints;
import interfaces.IFItemMigracion;

public class ConectorEnableConstraints extends ConectorDisableConstraints {

	public ConectorEnableConstraints(Connection con_){
		super(con_);
		this.con = con_;
		this.nombre = "ENABLE_CONSTRAINTS";			
	}
	
	@Override
	protected void cargarLista() throws SQLException {
		this.sql = this.armarSQL();
		this.sql +=
		"\norder by \n"+
	    "   decode(c.constraint_type,'P',0,'R',1,'U',2,'C',3,4),  \n"+ 
	    "   c.owner, \n"+
	    "   c.table_name, \n"+ 
	    "   c.constraint_name \n";			

		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemEnableConstraints tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = new ItemEnableConstraints();
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

}
