package imp.conectores;

import imp.Fabrica;
import imp.items.ItemChangePasswords;
import imp.items.ItemRebuildIndexes;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConectorChangePasswords extends ConectorAbstracto {

	public ConectorChangePasswords(Connection con_, List<String> excluir_){
		this.con = con_;
		this.nombre = "CHANGE_PASSWORDS";
		this.setItemsExcluir(excluir_);
	}
	
	public ConectorChangePasswords(Connection con_){
		this.con = con_;
		this.nombre = "CHANGE_PASSWORDS";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;

		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemChangePasswords tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemChangePassword();
			tbs.setUserName(rs.getString("username"));
			tbs.setPassword(rs.getString("password"));	
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
	}
	
	protected String armarSQL(){
		String sql = 
		"select username, password from dba_users where account_status = 'OPEN' \n";
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"	and username not in " + getCadenaItemsSQL(this.itemsExcluir) +" \n";
		}
		
		if (!this.itemsIncluir.isEmpty()){
			sql +=
			"	and username in " + getCadenaItemsSQL(this.itemsIncluir) +" \n";	
		}
		
		sql+="order by username";
		
		return sql;
	}

}
