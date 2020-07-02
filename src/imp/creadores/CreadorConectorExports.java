package imp.creadores;

import imp.conectores.ConectorExports;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorExports extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "EXPORTS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorExports(con);
	}

}
