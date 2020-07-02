package imp.conectores;

import imp.items.ItemTruncateTables;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConectorTruncateTables extends ConectorAbstracto {

	public ConectorTruncateTables(Connection con_){
		this.con = con_;
		this.nombre = "TRUNCATE_TABLES";		
	}
	
	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;

		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemTruncateTables tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = new ItemTruncateTables();
			tbs.setOwner(rs.getString("owner"));
			tbs.setNombreTabla(rs.getString("object_name"));
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
	}
	
	protected String armarSQL(){
		String sql=
		"select o.owner, o.object_name \n"+
		"from all_objects o \n"+
		"where \n"+
		"      o.object_type = 'TABLE' \n"; 
		
		if (!this.itemsExcluir.isEmpty()){
			sql+="	and o.owner not in " + getCadenaItemsSQL(this.itemsExcluir);
		}
		
		if (!this.itemsIncluir.isEmpty()){
			sql+="	and o.owner in " + getCadenaItemsSQL(this.itemsIncluir);	
		}		
		
		return sql+=" order by o.owner, o.object_name";
	}

}
