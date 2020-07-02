package imp.conectores;

import imp.Fabrica;
import imp.items.ItemFuncionProfile;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorFuncionesProfiles extends ConectorAbstracto {

	public ConectorFuncionesProfiles(Connection con_){
		this.nombre = "FUNCIONES_PROFILES";
		this.con = con_;		
	}
	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemFuncionProfile item = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			item = Fabrica.getInsItemFuncionProfile(con,rs.getString("nombre_funcion"));
			itemsMigracion.add(item);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
	}
	
	private String armarSQL() {
		return 
		"select \n"+ 
		"       distinct limit nombre_funcion \n"+
		"from \n"+
		"     dba_profiles \n"+
		"where \n"+
		"      resource_name ='PASSWORD_VERIFY_FUNCTION' and \n"+
		"      limit iS not null and \n"+
		"      limit not in ('UNLIMITED','DEFAULT','NULL')\n"+
		"order by limit";
		
	}

}
