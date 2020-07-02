package imp.creadores;

import imp.conectores.ConectorUnlockUsers;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorUnlockUsers extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "UNLOCK_USERS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorUnlockUsers(con);
	}

}
