package imp.creadores;

import interfaces.IFConectorMigracion;

import java.sql.Connection;


public abstract class CreadorArbstracto {
	public abstract boolean matches(String clave);
	public abstract IFConectorMigracion crearConector(Connection con);
}
