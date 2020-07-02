package imp.items;


import imp.items.datafiles.DataFileAbstracto;
import imp.items.datafiles.FabricaEstatica;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ItemTablespace implements IFItemMigracion {

	private String tbsName;
	private long initExt;
	private long nextExt;
	private long minExt;
	private long maxExt;
	private long pctInc;
	private long minExtLen;
	private String status;
	private String contents;
	private String logging;
	private String extMngmt;
	private String allocType;
	private String pluggedIn;
	protected String canonicalString=null;
	private boolean forzarLocal = false;
	private boolean forzarSegmentAuto = false;
	protected List<DataFileAbstracto> datafiles;
	
	public void setCanonicalString(String canonicalString) {
		this.canonicalString = canonicalString;
	}
	
	public String getTbsName() {
		return tbsName;
	}
	public void setTbsName(String tbsName) {
		this.tbsName = tbsName;
	}
	public long getInitExt() {
		return initExt;
	}
	public void setInitExt(long initExt) {
		this.initExt = initExt;
	}
	public long getNextExt() {
		return nextExt;
	}
	public void setNextExt(long nextExt) {
		this.nextExt = nextExt;
	}
	public long getMinExt() {
		return minExt;
	}
	public void setMinExt(long minExt) {
		this.minExt = minExt;
	}
	public long getMaxExt() {
		return maxExt;
	}
	public void setMaxExt(long maxExt) {
		this.maxExt = maxExt;
	}
	public long getPctInc() {
		return pctInc;
	}
	public void setPctInc(long pctInc) {
		this.pctInc = pctInc;
	}
	public long getMinExtLen() {
		return minExtLen;
	}
	public void setMinExtLen(long minExtLen) {
		this.minExtLen = minExtLen;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getLogging() {
		return logging;
	}
	public void setLogging(String logging) {
		this.logging = logging;
	}
	public String getExtMngmt() {
		if (this.isForzarLocal()){
			return "LOCAL";
		}
		return extMngmt;
	}
	public void setExtMngmt(String extMngmt) {
		this.extMngmt = extMngmt;
	}
	public String getAllocType() {
		return allocType;
	}
	public void setAllocType(String allocType) {
		this.allocType = allocType;
	}
	public String getPluggedIn() {
		return pluggedIn;
	}
	public void setPluggedIn(String pluggedIn) {
		this.pluggedIn = pluggedIn;
	}
	public List<DataFileAbstracto> getDatafiles() throws SQLException {
		return datafiles;
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

	private Connection con;

	public ItemTablespace(Connection con_) {
		this.con = con_;
	}

	private void cargarListaDatafiles() throws SQLException {
		String sql =
		"select \n"+
		"	file_name, \n"+
		"	file_id, \n"+
		"	tablespace_name, \n"+
		"	bytes, \n"+
		"	blocks, \n"+
		"	status, \n"+
		"	relative_fno, \n"+
		"	autoextensible, \n"+
		"	maxbytes, \n"+
		"	maxblocks, \n"+
		"	increment_by, \n"+
		"	user_bytes, \n"+
		"	user_blocks \n"+
		"from \n"+
		"	dba_data_files \n"+
		"where tablespace_name = ? \n" +
		"order by file_id \n";
		
		PreparedStatement ps = con.prepareStatement(sql);	
		ps.setString(1, this.getTbsName());
		ResultSet rs = ps.executeQuery();
		DataFileAbstracto df = null;
		this.datafiles = new ArrayList<DataFileAbstracto>();
		while (rs.next()){
			df = FabricaEstatica.getInstanciaDataFile();
			df.setFileName(rs.getString("file_name"));
			df.setFileID(rs.getLong("file_id"));
			df.setTbsName(rs.getString("tablespace_name"));
			df.setBytes(rs.getLong("bytes"));
			df.setBlocks(rs.getLong("blocks"));
			df.setStatus(rs.getString("status"));
			df.setRelFNO(rs.getLong("relative_fno"));
			df.setAutoExt(rs.getString("autoextensible"));
			df.setMaxBytes(rs.getLong("maxbytes"));
			df.setMaxBlocks(rs.getLong("maxblocks"));
			df.setIncBy(rs.getLong("increment_by"));
			df.setUsrBytes(rs.getLong("user_bytes"));
			df.setUsrBlocks(rs.getLong("user_blocks"));
			datafiles.add(df);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;		
		
	}	
	
	private String getStrStorage(){
		if (this.isForzarSegmentAuto()) {
			return "SEGMENT SPACE MANAGEMENT AUTO";
		}
		if (this.getAllocType().equals("UNIFORM")){
			return "UNIFORM SIZE "+(this.getInitExt()/1024)+"K";
		} else {
			return
			"DEFAULT STORAGE (INITIAL "+(this.getInitExt()/IFItemMigracion.BYTES_EN_KB)+"K NEXT "+(this.getNextExt()/IFItemMigracion.BYTES_EN_KB)+
			"K MINEXTENTS " + this.getMinExt() + " MAXEXTENTS "+this.getMaxExt()+ " PCTINCREASE "+this.getPctInc() + ") MINIMUM EXTENT "+
			this.getMinExtLen()+"K ";			
		}
	}
	
	@Override
	public String getCanonicalString() {
		String prompt="PROMPT creando tablespace \""+ this.getTbsName() +"\"\n";
		if (this.datafiles == null){
			try {
				this.cargarListaDatafiles();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		int c = 0;
		String coma="";
		String s = prompt+"CREATE TABLESPACE \"" + this.getTbsName() +"\" \n"+
		this.getLogging()+"\n"+
		"DATAFILE\n";
		for(DataFileAbstracto df: this.datafiles){			
			s += coma+df.toString();
			coma = ",";
			c++;
			if (c <= this.datafiles.size()){
				s+="\n";
			}
		}
		s+="EXTENT MANAGEMENT " +this.getExtMngmt()+"\n"+
		this.getStrStorage()+";";
		return s;
	}

}
