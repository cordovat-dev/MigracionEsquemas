package imp.creadores;

import imp.conectores.ConectorGrantsRoles;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorGrantsRoles extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "ROLES_GRANTS".matches(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorGrantsRoles(con);
	}

}
