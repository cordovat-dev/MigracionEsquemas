package imp.creadores;

import imp.conectores.ConectorSinonimos;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorSinonimos extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "PUBLIC_SYNONYMS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorSinonimos(con);
	}

}
