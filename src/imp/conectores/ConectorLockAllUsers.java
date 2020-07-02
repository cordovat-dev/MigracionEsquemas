package imp.conectores;

import imp.items.ItemLockUser;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConectorLockAllUsers extends ConectorAbstracto {

	public ConectorLockAllUsers(Connection con_) {
		super();
		this.con = con_;
		this.nombre = "LOCK_ALL_USERS";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;	
			
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemLockUser item = null;
		this.itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			item = new ItemLockUser() ;
			item.setUserName(rs.getString("username"));
			this.itemsMigracion.add(item);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;		
		
	}	
	
	protected String armarSQL(){
		
		String sql = 
		"select \n"+
		"      u.username \n"+
		"from dba_users u \n"+
		"where \n"+
		"      u.initial_rsrc_consumer_group <> 'SYS_GROUP' and \n"+
		"      u.username not in ('BCKP_','SYSMAN','SYS','SYSTEM') and \n"+
		"      u.account_status = 'OPEN' \n"+
		"order by u.username \n";
		
		return sql;	
	}
	
}
