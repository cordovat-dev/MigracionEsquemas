package imp.creadores;

import imp.conectores.ConectorPrivilegiosSistema;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorPrivilegiosSistema extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "USER_PRIVILEGES".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorPrivilegiosSistema(con);
	}

}
