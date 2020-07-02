package imp.conectores;

import imp.Fabrica;
import imp.items.ItemDBLinkPublico;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorDBLinksPublicos extends ConectorAbstracto {

	public ConectorDBLinksPublicos(Connection con_) {
		this.con = con_;
		this.nombre = "DBLINKS_PUBLICOS";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemDBLinkPublico item = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			item = Fabrica.getInsItemDBLink();
			item.setHost(rs.getString("host"));
			item.setName(rs.getString("db_link"));
			item.setPassword(rs.getString("password"));
			item.setUserID(rs.getString("username"));
			itemsMigracion.add(item);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;		}
	
	protected String armarSQL(){
		
		String sql =
		"select \n"+
		"      b.db_link, \n"+
		"      b.username, \n"+
		"      s.password, \n"+
		"      b.host \n"+
		"from \n"+
		"     dba_db_links b, \n"+
		"     sys.link$ s, \n"+
		"     sys.user$ u \n"+
		"where \n"+
		"     u.name = 'PUBLIC' and \n"+
		"     s.owner# = u.user# and \n"+
		"     b.owner = u.name and \n"+
		"     b.db_link = s.name \n"+
		"order by b.db_link\n";
			
		return sql;
		
	}	

}
