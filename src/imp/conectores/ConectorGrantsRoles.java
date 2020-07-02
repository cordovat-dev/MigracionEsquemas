package imp.conectores;

import imp.Fabrica;
import imp.items.ItemGrantRol;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorGrantsRoles extends ConectorAbstracto {

	public ConectorGrantsRoles(Connection con_) {
		this.con = con_;
		this.nombre = "GRANT_ROLES";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;	
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemGrantRol tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemRolUsuario();
			tbs.setNombreRol(rs.getString("granted_role"));
			tbs.setNombreUsuario(rs.getString("grantee"));
			tbs.setAdminOption(rs.getString("admin_option"));
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;		
		
	}
	
	private String armarSQL(){
		
		String sql =
		"select \n"+
		"      rp.granted_role, \n"+
		"      rp.grantee, \n"+
		"      rp.admin_option \n"+
		"from \n"+
		"    dba_role_privs rp \n"; 
		
		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"where rp.default_role = 'YES' and \n";
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
		
		sql+="\norder by rp.grantee, rp.grantee";
		
		return sql;
		
	}		

}
