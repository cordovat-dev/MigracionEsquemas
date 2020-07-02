package imp.conectores;

import imp.Fabrica;
import imp.items.ItemPrivilegioUsuario;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorPrivilegiosSistema extends ConectorAbstracto {

	public ConectorPrivilegiosSistema(Connection con_) {
		this.con = con_;
		this.nombre = "PRIVILEGIOS_SISTEMA";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemPrivilegioUsuario tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemPrivilegioUsuario();
			tbs.setNombrePrivilegio(rs.getString("privilege"));
			tbs.setNombreUsuario(rs.getString("grantee"));
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
		"      sp.privilege, \n"+
		"      sp.grantee \n"+
		"from \n"+
		"    dba_sys_privs sp \n";
		
		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"where \n";
		}
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"	sp.grantee not in " + getCadenaItemsSQL(this.itemsExcluir);
		}
		
		if (!this.itemsIncluir.isEmpty()){
			if (!this.itemsExcluir.isEmpty()) {
				sql += " and\n";
			}
			sql +=
			"	sp.grantee in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql+="\norder by sp.grantee, sp.privilege";
		
		return sql;
		
	}	
}
