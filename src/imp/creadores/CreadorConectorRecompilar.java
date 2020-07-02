package imp.creadores;

import imp.conectores.ConectorRecompilar;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorRecompilar extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "RECOMPILAR_OBJETOS".matches(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorRecompilar();
	}

}
