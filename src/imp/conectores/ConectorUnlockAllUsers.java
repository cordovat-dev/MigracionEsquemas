package imp.conectores;

import imp.items.ItemUnlockUser;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConectorUnlockAllUsers extends ConectorLockAllUsers {

	public ConectorUnlockAllUsers(Connection con_) {
		super(con_);
		this.nombre = "UNLOCK_ALL_USERS";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;	
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemUnlockUser item = null;
		this.itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			item = new ItemUnlockUser() ;
			item.setUserName(rs.getString("username"));
			this.itemsMigracion.add(item);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;		
	}	
}
