package imp.conectores;

import imp.Fabrica;
import imp.items.ItemPermisoObjeto;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorPermisosObjetos extends ConectorAbstracto {

	public ConectorPermisosObjetos(Connection con_) {
		this.con = con_;
		this.nombre = "PERMISOS_OBJETOS";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;
					
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemPermisoObjeto tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemPermisoObjeto(con);
			tbs.setDueno(rs.getString("owner"));
			tbs.setObjeto(rs.getString("table_name"));
			tbs.setPrivilegio(rs.getString("privilege"));
			tbs.setUsuario(rs.getString("grantee"));
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
		"      tb.privilege, \n"+
		"      tb.owner, \n"+
		"      tb.table_name, \n"+
		"      tb.grantee \n"+
		"from \n"+
		"    dba_tab_privs tb \n";
		
		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"where \n";
		}	
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"	tb.grantee not in " + getCadenaItemsSQL(this.itemsExcluir);
		}
		
		if (!this.itemsIncluir.isEmpty()){
			if (!this.itemsExcluir.isEmpty()) {
				sql += " and\n";
			}
			sql +=
			"	tb.grantee in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql += "\norder by tb.grantee, tb.owner, tb.table_name, tb.privilege";
		
		return sql;
		
	}		

}
