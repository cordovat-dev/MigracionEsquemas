package imp.items;

import interfaces.IFItemMigracion;

public class ItemPermisoObjeto implements IFItemMigracion {
	protected String privilegio;
	protected String dueno;
	protected String objeto;
	protected String usuario;
	@Override
	public String getCanonicalString() {
		String s1 = "PROMPT ejecutando GRANT %s ON \"%s\".\"%s\" TO \"%s\"\n";
		String s2 = "GRANT %s ON \"%s\".\"%s\" TO \"%s\";";
		return String.format(s1, this.privilegio, this.dueno, this.objeto, this.usuario)+
			   String.format(s2, this.privilegio, this.dueno, this.objeto, this.usuario);
	}
	public String getPrivilegio() {
		return privilegio;
	}
	public void setPrivilegio(String privilegio) {
		this.privilegio = privilegio;
	}
	public String getDueno() {
		return dueno;
	}
	public void setDueno(String dueno) {
		this.dueno = dueno;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
