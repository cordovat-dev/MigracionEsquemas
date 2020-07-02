package imp.creadores;

import imp.conectores.ConectorChangePasswords;
import imp.conectores.ConectorPerfiles;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorChangePasswords extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "CHANGE_PASSWORDS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorChangePasswords(con);
	}

}
