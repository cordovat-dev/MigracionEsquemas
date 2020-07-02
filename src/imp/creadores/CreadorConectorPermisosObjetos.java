package imp.creadores;

import imp.conectores.ConectorPermisosObjetos;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorPermisosObjetos extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "OBJECT_PRIVILEGES".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorPermisosObjetos(con);
	}

}
