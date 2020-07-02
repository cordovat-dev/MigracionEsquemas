package imp.creadores;

import imp.conectores.ConectorImports;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorImports extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "IMPORTS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorImports(con);
	}

}
