package imp.creadores;

import imp.conectores.ConectorUsuarios;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorUsuarios extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "USERS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorUsuarios(con);
	}

}
