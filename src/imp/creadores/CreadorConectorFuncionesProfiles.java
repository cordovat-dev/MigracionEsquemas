package imp.creadores;

import imp.conectores.ConectorFuncionesProfiles;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorFuncionesProfiles extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "PROFILE_FUNCTIONS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorFuncionesProfiles(con);
	}

}
