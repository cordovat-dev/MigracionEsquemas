package imp.creadores;

import imp.conectores.ConectorLockAllUsers;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorLockAllUsers extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "LOCK_ALL_USERS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorLockAllUsers(con);
	}

}
