package imp.items.datafiles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class Tablespace extends TablespaceAbstracto {
	private Connection con;
	public static final long BITES_EN_KB = 1024;
	public Tablespace(Connection con_){
		con = con_;
	}

	public Tablespace() {
	}
	
	public List<DataFileAbstracto> getDatafiles() throws SQLException {
		if (super.datafiles == null){
			cargarListaDatafiles();
		} 
		return super.datafiles;
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
			super.datafiles.add(df);
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
			"DEFAULT STORAGE (INITIAL "+(this.getInitExt()/Tablespace.BITES_EN_KB)+"K NEXT "+(this.getNextExt()/Tablespace.BITES_EN_KB)+
			"K MINEXTENTS " + this.getMinExt() + " MAXEXTENTS "+this.getMaxExt()+ " PCTINCREASE "+this.getPctInc() + ") MINIMUM EXTENT "+
			this.getMinExtLen()+"K ";			
		}
	}
	
	public String toString(){
		if (this.datafiles == null){
			try {
				this.cargarListaDatafiles();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		int c = 0;
		String coma="";
		String s =
		"CREATE TABLESPACE \"" + this.getTbsName() +"\" \n"+
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
		this.getStrStorage()+";\n";
		return s;
		
	}
}
