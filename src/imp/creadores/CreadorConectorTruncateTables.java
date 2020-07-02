package imp.creadores;

import imp.conectores.ConectorTruncateTables;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorTruncateTables extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "TRUNCATE_TABLES".matches(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorTruncateTables(con);
	}

}
