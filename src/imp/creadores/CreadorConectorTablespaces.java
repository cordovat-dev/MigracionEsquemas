package imp.creadores;

import imp.conectores.ConectorTablespaces;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorTablespaces extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "TABLESPACES".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorTablespaces(con);
	}

}
