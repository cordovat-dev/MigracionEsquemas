package imp.creadores;

import imp.conectores.ConectorRolesGlobales;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorRolesGlobales extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "ALL_ROLES".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorRolesGlobales(con);
	}

}
