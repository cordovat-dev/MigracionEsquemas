package imp.items;

import interfaces.IFItemMigracion;

public class ItemExport implements IFItemMigracion {
	
	protected String usuario;

	@Override
	public String getCanonicalString() {
		return String.format("exp file=exp%s.dmp log=exp%s.log OWNER=%s", this.usuario, this.usuario, this.usuario);
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
