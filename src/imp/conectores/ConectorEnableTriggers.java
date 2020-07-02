package imp.conectores;

import imp.Fabrica;
import imp.items.ItemEnableTriggers;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConectorEnableTriggers extends ConectorDisableTriggers {

	public ConectorEnableTriggers(Connection con_){
		super(con_);
		this.con = con_;
		this.nombre = "ENABLE_TRIGGERS";		
	}
	
	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;

		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemEnableTriggers tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemsEnableAllTriggers();
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
	
}
