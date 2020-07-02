package imp.items.datafiles;
import java.sql.SQLException;
import java.util.List;



public abstract class TablespaceAbstracto {
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
	private boolean forzarLocal = false;
	private boolean forzarSegmentAuto = false;
	protected List<DataFileAbstracto> datafiles;
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

	
	
}
