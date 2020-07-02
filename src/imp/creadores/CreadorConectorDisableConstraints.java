package imp.creadores;

import imp.conectores.ConectorDisableConstraints;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorDisableConstraints extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "DISABLE_CONSTRAINTS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorDisableConstraints(con);
	}

}
