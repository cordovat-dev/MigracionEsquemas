package imp.conectores;

import imp.Fabrica;
import imp.items.ItemUsuario;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ConectorUsuarios extends ConectorAbstracto {

	public ConectorUsuarios(Connection con_, List<String> excluir_){
		this.con = con_;
		this.nombre = "USUARIOS";
		this.setItemsExcluir(excluir_);
	}
	
	public ConectorUsuarios(Connection con_){
		this.con = con_;
		this.nombre = "USUARIOS";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;	
			
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemUsuario item = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			item = Fabrica.getInsItemUsuario();
			item.setDefaultTablespace(rs.getString("default_tablespace"));
			item.setPassword(rs.getString("password"));			
			item.setProfile(rs.getString("profile"));
			item.setTemporaryTablespace(rs.getString("temporary_tablespace"));
			item.setUserName(rs.getString("username"));
			item.setLocked(rs.getString("locked"));
			item.setPwdExpired(rs.getString("pwd_expire"));
			itemsMigracion.add(item);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;		
		
	}

	protected String armarSQL(){
		
		String sql =

		"select \n"+
		"      u.username, \n"+
		"      u.profile, \n"+
		"      u.password, \n"+
		"      u.default_tablespace, \n"+
		"      u.temporary_tablespace, \n"+  
		"      decode(u.lock_date,null,'UNLOCK','LOCK') locked, \n" +
        "      decode(u.expiry_date,null,'-','PASSWORD EXPIRE') pwd_expire \n" +	
		"from dba_users u \n";
		
		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"where \n";
		}
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"	username not in " + getCadenaItemsSQL(this.itemsExcluir);
		}
		
		if (!this.itemsIncluir.isEmpty()){
			if (!this.itemsExcluir.isEmpty()) {
				sql += " and\n";
			}
			sql +=
			"	username in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql+="\norder by u.username";
		
		return sql;
		
	}	

}
