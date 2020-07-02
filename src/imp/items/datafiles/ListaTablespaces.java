package imp.items.datafiles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ListaTablespaces {
	private List<TablespaceAbstracto> lista = null;
	private List<String> listaExcluir = new ArrayList<String>();
	private List<String> listaFiltrar = new ArrayList<String>();
	private Connection con;
	private boolean forzarLocal = false;
	private boolean forzarSegmentAuto = false;
	public ListaTablespaces(Connection con_) throws SQLException{
		con = con_;	
	}
	public List<TablespaceAbstracto> getLista() throws SQLException {
		if (lista == null){
			cargarLista();
		} 
		return lista;
	}
	private void cargarLista() throws SQLException {
		String sql = this.armarSQL();
			
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		TablespaceAbstracto tbs = null;
		lista = new ArrayList<TablespaceAbstracto>();
		while (rs.next()){
			tbs = FabricaEstatica.getInstanciaTablespace(con);
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
			lista.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
		
	}
	public List<String> getListaExcluir() {
		return listaExcluir;
	}
	public void setListaExcluir(List<String> listaExcluir) {
		this.listaExcluir = listaExcluir;
	}
	public List<String> getListaFiltrar() {
		return listaFiltrar;
	}
	public void setListaFiltrar(List<String> listaFiltrar) {
		this.listaFiltrar = listaFiltrar;
	}
	
	public void setListaFiltrar(String[] l){
		for (String s: l){
			this.listaFiltrar.add(s);
		}
	}
	
	public void setListaExcluir(String[] l){
		for (String s: l){
			this.listaExcluir.add(s);
		}		
	}	
	
	private String armarSQL(){
		
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
		"	dba_tablespaces \n";
		
		if (!this.listaExcluir.isEmpty() || !this.listaFiltrar.isEmpty()){
			sql +=
			"where \n";
		}
		
		if (!this.listaExcluir.isEmpty()){
			sql +=
			"	tablespace_name not in " + ListaTablespaces.getCadenaItemsSQL(this.listaExcluir);
		}
		
		if (!this.listaFiltrar.isEmpty()){
			if (!this.listaExcluir.isEmpty()) {
				sql += " and\n";
			}
			sql +=
			"	tablespace_name in " + ListaTablespaces.getCadenaItemsSQL(this.listaFiltrar);	
		}
		
		return sql;
		
	}
	
	
	public static String getCadenaItemsSQL(List<String> l){
		String retorno="( ";
		int c = 0;
		for (String s: l){
			c++;
			if (c >1){
				retorno += ",'"+s+"'";
			} else {
				retorno += "'"+s+"'";
			}
		}
		return retorno+" )";
		
	}
	public boolean isForzarLocal() {
		return forzarLocal;
	}
	public void setForzarLocal(boolean forzarLocal) {
		this.forzarLocal = forzarLocal;
	}
	public boolean isForzarSegmentAuto() {
		return forzarSegmentAuto;
	}
	public void setForzarSegmentAuto(boolean forzarSegmentAuto) {
		this.forzarSegmentAuto = forzarSegmentAuto;
	}
	
	
}
