package imp.items;

import interfaces.IFItemMigracion;

public class ItemPrivilegioUsuario implements IFItemMigracion {
	protected String nombreUsuario;
	protected String nombrePrivilegio;
	
	@Override
	public String getCanonicalString() {
		String s =
		"GRANT %s TO \"%s\";\nPROMPT asignando privilegio %s a %s;";
		return String.format(s, this.nombrePrivilegio, this.nombreUsuario,this.nombrePrivilegio, this.nombreUsuario);
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getNombrePrivilegio() {
		return nombrePrivilegio;
	}
	public void setNombrePrivilegio(String nombrePrivilegio) {
		this.nombrePrivilegio = nombrePrivilegio;
	}
}
