package imp.creadores;

import imp.conectores.ConectorDBLinksPublicos;
import interfaces.IFConectorMigracion;

import java.sql.Connection;


public class CreadorConectorDBLinksPublicos extends CreadorArbstracto {

	@Override
	public boolean matches(String clave) {
		return "DBLINKS".equals(clave);
	}

	@Override
	public IFConectorMigracion crearConector(Connection con) {
		return new ConectorDBLinksPublicos(con);
	}

}
