package imp.conectores;

import java.sql.Connection;

public class ConectorPerfilesGlobales extends ConectorPerfiles {

	public ConectorPerfilesGlobales(Connection con_) {
		super(con_);
		this.nombre = "ALL_PROFILES";
	}

	@Override
	protected String armarSQL(){
		
		String sql =
		"select distinct p.profile from dba_users p where p.profile<>'DEFAULT' order by p.profile\n"; 
		
		return sql;
		
	}		
}
