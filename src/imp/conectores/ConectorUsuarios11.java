package imp.conectores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import imp.ArchivoDeSalidaNoEspecificadoException;
import imp.Fabrica;
import imp.items.ItemUsuario;
import interfaces.IFConectorMigracion;
import interfaces.IFItemMigracion;
import interfaces.IFManejadorSalida;

public class ConectorUsuarios11 extends ConectorUsuarios {

	
	public ConectorUsuarios11(Connection con_, List<String> excluir_){
		super(con_,excluir_);
}
	
	public ConectorUsuarios11(Connection con) {
		super(con);
	}
	
	@Override
	protected String armarSQL(){
		
		String sql =

		"select \n"+
		"      du.username, \n"+
		"      du.profile, \n"+
		"      su.password, \n"+
		"      du.default_tablespace, \n"+
		"      du.temporary_tablespace, \n"+
		"      decode(du.lock_date,null,'UNLOCK','LOCK') locked, \n"+
		"      decode(du.expiry_date,null,'-','PASSWORD EXPIRE') pwd_expire \n"+
		"from \n"+
		"     dba_users du join sys.user$ su on (du.username = su.name) \n";
		
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
		
		sql+="\norder by du.username";
		
		return sql;
		
	}	
}
