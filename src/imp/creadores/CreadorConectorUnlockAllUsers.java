package imp.creadores;

import java.sql.Connection;

import imp.conectores.ConectorUnlockAllUsers;
import interfaces.IFConectorMigracion;

public class CreadorConectorUnlockAllUsers extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "UNLOCK_ALL_USERS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorUnlockAllUsers(con);
	}

}
