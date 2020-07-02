package imp.conectores;

/*import FabricaEstatica;
import ListaTablespaces;
import TablespaceAbstracto;*/

import imp.Fabrica;
import imp.items.ItemTablespace;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class ConectorTablespaces extends ConectorAbstracto  {
	private boolean forzarSegmentAuto = false;
	private boolean forzarLocal = false;
	public ConectorTablespaces(Connection con_) {
		this.nombre = "TABLESPACES";
		this.con = con_;
		//this.cargarLista();
	}
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		this.sql = sql;
			
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemTablespace tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemTablespace(con);
			tbs.setTbsName(rs.getString("tablespace_name"));
			tbs.setInitExt(rs.getLong("initial_extent"));
			tbs.setNextExt(rs.getLong("next_extent"));
			tbs.setMinExt(rs.getLong("min_extents"));
			tbs.setMaxExt(rs.getLong("max_extents"));
			tbs.setPctInc(rs.getLong("pct_increase"));
			tbs.setMinExtLen(rs.getLong("min_extlen"));
			tbs.setStatus(rs.getString("status"));
			tbs.setLogging(rs.getString("logging"));
			tbs.setExtMngmt(rs.getString("extent_management"));
			tbs.setAllocType(rs.getString("allocation_type"));
			tbs.setPluggedIn(rs.getString("plugged_in"));
			tbs.setForzarLocal(this.isForzarLocal());
			tbs.setForzarSegmentAuto(this.isForzarSegmentAuto());
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
		
	}
	
	private boolean isForzarSegmentAuto() {
		return forzarSegmentAuto ;
	}
	
	private boolean isForzarLocal() {
		return forzarLocal ;
	}
	
	protected String armarSQL(){
		
		String sql =

		"select \n"+
		"	tablespace_name, \n"+
		"	initial_extent, \n"+
		"	next_extent, \n"+
		"	min_extents, \n"+
		"	max_extents, \n"+
		"	pct_increase, \n"+
		"	min_extlen, \n"+
		"	status, \n"+
		"	logging, \n"+
		"	extent_management, \n"+
		"	allocation_type, \n"+
		"	plugged_in \n"+
		"from \n"+
		"	dba_tablespaces \n"+
		"where \n"+
		"	tablespace_name in ( \n"+
		armarSQL2()+
		"\n) order by tablespace_name";

		return sql;
		
	}	
	
	public void setForzarSegmentAuto(boolean forzarSegmentAuto) {
		this.forzarSegmentAuto = forzarSegmentAuto;
	}
	public void setForzarLocal(boolean forzarLocal) {
		this.forzarLocal = forzarLocal;
	}
	
	protected String armarSQL2(){
		String sql =
			
		"    select \n"+
		"         distinct t.tablespace_name \n"+
		"    from \n"+
		"    ( \n"+
		"        select \n"+
		"          x.tablespace_name, \n"+
		"          x.owner  usuario \n"+
		"        from \n"+
		"             sys.dba_extents x \n";

		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"        where \n";
		}
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"              x.owner not in " + getCadenaItemsSQL(this.itemsExcluir);	
		}
		
		if (!this.itemsIncluir.isEmpty()){
			if (!this.itemsExcluir.isEmpty()) {
				sql += "	 and\n";
			}
			sql +=
			"              x.owner in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		
		sql+=
		"\n        union \n"+
		"        select \n"+
		"          o.tablespace_name, \n"+
		"          o.username usuario \n"+
		"        from \n"+
		"             dba_ts_quotas o \n";
		
		if (!this.itemsExcluir.isEmpty() || !this.itemsIncluir.isEmpty()){
			sql +=
			"        where \n";
		}
		
		if (!this.itemsExcluir.isEmpty()){
			sql +=
			"              o.username not in " + getCadenaItemsSQL(this.itemsExcluir);	
		}
		
		if (!this.itemsIncluir.isEmpty()){
			if (!this.itemsExcluir.isEmpty()) {
				sql += "	 and\n";
			}
			sql +=
			"              o.username in " + getCadenaItemsSQL(this.itemsIncluir);	
		}
		sql+="    ) t \n";
		return sql;		
	}
	
}
