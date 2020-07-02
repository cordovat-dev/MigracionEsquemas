package imp.creadores;

import imp.conectores.ConectorPerfiles;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorPerfiles extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "PROFILES".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorPerfiles(con);
	}

}
