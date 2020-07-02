package imp.conectores;

import java.sql.Connection;

public class ConectorRolesGlobales extends ConectorRoles {

	public ConectorRolesGlobales(Connection con_) {
		super(con_);
		this.nombre = "ALL_ROLES";
	}
	
	@Override
	protected String armarSQL(){
		String sql =
		"select \n"+
		"      r.role \n"+
		"from \n"+
		"    dba_roles r \n"+
		"order by r.role \n"; 		
		
		return sql;
		
	}

}
