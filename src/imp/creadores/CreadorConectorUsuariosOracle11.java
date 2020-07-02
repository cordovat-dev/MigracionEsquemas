package imp.creadores;

import imp.conectores.ConectorUsuarios;
import imp.conectores.ConectorUsuarios11;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorUsuariosOracle11 extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "USERS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorUsuarios11(con);
	}

}
