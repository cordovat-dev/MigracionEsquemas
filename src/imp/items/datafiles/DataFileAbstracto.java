package imp.items.datafiles;

public abstract class DataFileAbstracto {
	public static final long BYTES_EN_MEGAS = 1024*1024;
	
	private String fileName;
	private long fileID;
	private String tbsName;
	private long bytes;
	private long blocks;
	private String status;
	private long relFNO;
	private String autoExt;
	private long maxBytes;
	private long maxBlocks;
	private long incBy;
	private long usrBytes;
	private long usrBlocks;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileID() {
		return fileID;
	}
	public void setFileID(long fileID) {
		this.fileID = fileID;
	}
	public String getTbsName() {
		return tbsName;
	}
	public void setTbsName(String tbsName) {
		this.tbsName = tbsName;
	}
	public long getBytes() {
		return bytes;
	}
	public void setBytes(long bytes) {
		this.bytes = bytes;
	}
	public long getBlocks() {
		return blocks;
	}
	public void setBlocks(long blocks) {
		this.blocks = blocks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getRelFNO() {
		return relFNO;
	}
	public void setRelFNO(long relFNO) {
		this.relFNO = relFNO;
	}
	public String getAutoExt() {
		return autoExt;
	}
	public void setAutoExt(String autoExt) {
		this.autoExt = autoExt;
	}
	public long getMaxBytes() {
		return maxBytes;
	}
	public void setMaxBytes(long maxBytes) {
		this.maxBytes = maxBytes;
	}
	public long getMaxBlocks() {
		return maxBlocks;
	}
	public void setMaxBlocks(long maxBlocks) {
		this.maxBlocks = maxBlocks;
	}
	public long getIncBy() {
		return incBy;
	}
	public void setIncBy(long incBy) {
		this.incBy = incBy;
	}
	public long getUsrBytes() {
		return usrBytes;
	}
	public void setUsrBytes(long usrBytes) {
		this.usrBytes = usrBytes;
	}
	public long getUsrBlocks() {
		return usrBlocks;
	}
	public void setUsrBlocks(long usrBlocks) {
		this.usrBlocks = usrBlocks;
	}
	
}
