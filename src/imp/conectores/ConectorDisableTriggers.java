package imp.conectores;

import imp.Fabrica;
import imp.items.ItemDisableTriggers;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConectorDisableTriggers extends ConectorAbstracto {

	public ConectorDisableTriggers(Connection con_){
		super();
		this.con = con_;
		this.nombre = "DISABLE_TRIGGERS";		
	}
	
	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;

		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemDisableTriggers tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemsDisableAllTriggers();
			tbs.setOwner(rs.getString("owner"));
			tbs.setNombre(rs.getString("trigger_name"));	
			tbs.setOwnerTabla(rs.getString("table_owner"));
			tbs.setNombreTabla(rs.getString("table_name"));				
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
	}

	protected String armarSQL(){
		String sql =
				
		"select \n" +
		"	t.owner, \n" +
		"	t.trigger_name, \n" +
		"	t.table_owner, \n" +
		"	t.table_name \n"+
		"from all_triggers t \n" +
		"where \n" +
		"	status = 'ENABLED' \n";
		

		if (!this.itemsExcluir.isEmpty()){
			sql+="	and t.owner not in " + getCadenaItemsSQL(this.itemsExcluir);
		}
		
		if (!this.itemsIncluir.isEmpty()){
			sql+="	and t.owner in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql+=" order by table_owner, table_name, trigger_name ";
		return sql;
	} 
}
