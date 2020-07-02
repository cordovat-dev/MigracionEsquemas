package imp.creadores;

import imp.conectores.ConectorLockUsers;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorLockUsers extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "LOCK_USERS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorLockUsers(con);
	}

}
