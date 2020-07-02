package imp.items.datafiles;


import java.sql.Connection;



public class FabricaEstatica {

	public static TablespaceAbstracto getInstanciaTablespace(Connection con) {
		
		return new Tablespace(con);
	}

	public static DataFileAbstracto getInstanciaDataFile() {
		return new DataFile();
	}



}
