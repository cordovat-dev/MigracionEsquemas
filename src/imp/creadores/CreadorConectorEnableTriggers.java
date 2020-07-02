package imp.creadores;

import imp.conectores.ConectorEnableTriggers;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorEnableTriggers extends CreadorArbstracto {
	
	@Override
	public boolean matches(String clave) {
		return "ENABLE_ALL_TRIGGERS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorEnableTriggers(con);
	}
}
