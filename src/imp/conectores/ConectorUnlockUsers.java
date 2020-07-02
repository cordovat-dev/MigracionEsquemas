package imp.conectores;

import java.sql.Connection;

public class ConectorUnlockUsers extends ConectorUnlockAllUsers {

	public ConectorUnlockUsers(Connection con_) {
		super(con_);
		this.nombre = "UNLOCK_USERS";
	}

	protected String armarSQL(){
		
		String sql = 
		"select \n"+
		"      u.username \n"+
		"from dba_users u \n"+
		"where \n"+
		"      u.account_status = 'OPEN' \n";

		if (!this.itemsExcluir.isEmpty()){
			sql+="	and u.username not in " + getCadenaItemsSQL(this.itemsExcluir);
		}
		
		if (!this.itemsIncluir.isEmpty()){
			sql+="	and u.username in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql+=" order by u.username \n";
		return sql;	
	}	
	
}
