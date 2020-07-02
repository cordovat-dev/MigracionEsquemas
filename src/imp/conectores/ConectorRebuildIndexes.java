package imp.conectores;

import imp.Fabrica;
import imp.items.ItemEnableTriggers;
import imp.items.ItemRebuildIndexes;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConectorRebuildIndexes extends ConectorAbstracto {

	public ConectorRebuildIndexes(Connection con_) {
		this.con = con_;
		this.nombre = "REBUILD_INDEXES";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;

		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemRebuildIndexes tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemRebuildIndexes();
			tbs.setOwner(rs.getString("owner"));
			tbs.setNombre(rs.getString("index_name"));	
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
		"    i.owner, \n"+
		"    i.index_name \n"+
		"from \n"+
		"    dba_indexes i \n"+
		"where 1=1 \n";
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"	and i.table_owner not in " + getCadenaItemsSQL(this.itemsExcluir) +" \n";
		}
		
		if (!this.itemsIncluir.isEmpty()){
			sql +=
			"	and i.table_owner in " + getCadenaItemsSQL(this.itemsIncluir) +" \n";	
		}
		
		sql+="order by i.owner, i.index_name";
		
		return sql;
		
	}		

}
