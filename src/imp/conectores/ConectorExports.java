package imp.conectores;

import imp.Fabrica;
import imp.items.ItemExport;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConectorExports extends ConectorAbstracto {

	public ConectorExports(Connection con_) {
		this.con = con_;
		this.nombre = "EXPORTS";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemExport tbs = null;
		this.itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemExport();
			tbs.setUsuario(rs.getString("owner"));
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
		"      distinct o.owner owner \n"+
		"from \n"+
		"    dba_objects o \n";
		
		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"where \n";
		}
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"	owner not in " + getCadenaItemsSQL(this.itemsExcluir);
		}			
		
		if (!this.itemsIncluir.isEmpty()){
			if (!this.itemsExcluir.isEmpty()) {
				sql += " and\n";
			}
			sql +=
			"	owner in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql += "\norder by o.owner";
		
		return sql;
		
	}	

}
