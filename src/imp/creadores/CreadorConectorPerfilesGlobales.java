package imp.creadores;

import imp.conectores.ConectorPerfilesGlobales;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorPerfilesGlobales extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "ALL_PROFILES".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorPerfilesGlobales(con);
	}

}
