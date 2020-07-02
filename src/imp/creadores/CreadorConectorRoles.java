package imp.creadores;

import imp.conectores.ConectorRoles;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorRoles extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "ROLES".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorRoles(con);
	}

}
