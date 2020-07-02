package imp.conectores;

import imp.Fabrica;
import imp.items.ItemSinonimo;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorSinonimos extends ConectorAbstracto {

	public ConectorSinonimos(Connection con_) {
		this.con = con_;
		this.nombre = "SINONIMOS";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;
			
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemSinonimo tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemSinonimo(con);
			tbs.setDueno(rs.getString("table_owner"));
			tbs.setNombre(rs.getString("synonym_name"));
			tbs.setTabla(rs.getString("table_name"));
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
	}
	
	private String armarSQL(){
		
		String sql =

		"select \n"+
		"      s.synonym_name, \n"+
		"      s.table_owner, \n"+
		"      s.table_name, \n"+
		"      nvl(s.db_link,'-') db_link \n"+
		"from \n"+
		"    dba_synonyms s \n"+
		"where \n"+
	    " s.owner = 'PUBLIC' ";	
		
		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"and \n";
		}
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"	s.table_owner not in " + getCadenaItemsSQL(this.itemsExcluir);
		}
		
		if (!this.itemsIncluir.isEmpty()){
			if (!this.itemsExcluir.isEmpty()) {
				sql += " and\n";
			}
			sql +=
			"	s.table_owner in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql+="\norder by s.synonym_name";
		
		return sql;
		
	}	

}
