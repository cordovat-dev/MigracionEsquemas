package imp.creadores;

import imp.conectores.ConectorRebuildIndexes;
import interfaces.IFConectorMigracion;

import java.sql.Connection;

public class CreadorConectorRebuildIndexes extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "REBUILD_INDEXES".matches(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorRebuildIndexes(con);
	}

}
