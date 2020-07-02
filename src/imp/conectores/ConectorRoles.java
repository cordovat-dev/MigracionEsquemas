package imp.conectores;

import imp.Fabrica;
import imp.items.ItemRol;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorRoles extends ConectorAbstracto {

	public ConectorRoles(Connection con_) {
		this.con = con_;
		this.nombre = "ROLES";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;

		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemRol tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemRol();
			tbs.setNombre(rs.getString("role"));
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
		"      distinct rp.granted_role role \n"+
		"from \n"+
		"    dba_role_privs rp \n"; 
		

		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"where \n";
		}
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"	rp.grantee not in " + getCadenaItemsSQL(this.itemsExcluir);
		}
		
		if (!this.itemsIncluir.isEmpty()){
			if (!this.itemsExcluir.isEmpty()) {
				sql += " and\n";
			}
			sql +=
			"	rp.grantee in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql+="\norder by rp.granted_role";
		
		return sql;
		
	}	
	
	
}
