package imp.creadores;

import imp.conectores.ConectorDisableTriggers;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorDisableTriggers extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "DISABLE_ALL_TRIGGERS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorDisableTriggers(con);
	}

}
