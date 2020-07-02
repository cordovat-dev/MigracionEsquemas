package imp.creadores;

import imp.conectores.ConectorTablespacesOracle9;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorTablespacesOracle9 extends CreadorConectorTablespaces {

	@Override
	public IFConectorMigracion crearConector(Connection con) {		
		return new ConectorTablespacesOracle9(con);
	}

}
