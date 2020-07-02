package imp.items.datafiles;


public class DataFile extends DataFileAbstracto {
	public String toString(){
		return "'"+this.getFileName()+"' SIZE "+(this.getBytes()/DataFile.BYTES_EN_MEGAS)+"M REUSE";
	}
}
