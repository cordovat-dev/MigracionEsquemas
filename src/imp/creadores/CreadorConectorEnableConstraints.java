package imp.creadores;

import imp.conectores.ConectorEnableConstraints;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorEnableConstraints extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "ENABLE_CONSTRAINTS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorEnableConstraints(con);
	}

}
