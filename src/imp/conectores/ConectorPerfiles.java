package imp.conectores;

import imp.Fabrica;
import imp.items.ItemPerfil;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorPerfiles extends ConectorAbstracto {

	public ConectorPerfiles(Connection con_){
		this.con = con_;
		this.nombre = "PROFILES";		
	}
	
	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemPerfil tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemPerfil(this.con);
			tbs.setNombre(rs.getString("profile"));
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
	}
	
	protected String armarSQL(){
		
		String sql =
		"select distinct p.profile from dba_users p where p.profile<>'DEFAULT' \n"; 

		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"and \n";
		}
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"	username not in " + getCadenaItemsSQL(this.itemsExcluir);
		}			
		
		if (!this.itemsIncluir.isEmpty()){
			if (!this.itemsExcluir.isEmpty()) {
				sql += " and\n";
			}
			sql +=
			"	username in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql +="  order by p.profile \n";
		
		return sql;
		
	}		
	

}
