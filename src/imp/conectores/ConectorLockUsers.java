package imp.conectores;

import java.sql.Connection;

public class ConectorLockUsers extends ConectorLockAllUsers {

	public ConectorLockUsers(Connection con_) {
		super(con_);
		this.nombre = "LOCK_USERS";
	}
	
	protected String armarSQL(){
		
		String sql = 
		"select \n"+
		"      u.username \n"+
		"from dba_users u \n"+
		"where \n"+
		"      u.initial_rsrc_consumer_group <> 'SYS_GROUP' and \n"+
		"      u.username not in ('BCKP','SYSMAN','SYS','SYSTEM') and \n"+
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
