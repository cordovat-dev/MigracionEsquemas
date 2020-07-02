package imp.items;

import interfaces.IFItemMigracion;

public class ItemRol implements IFItemMigracion {
	
	protected String nombre;

	@Override
	public String getCanonicalString() {
		return String.format("PROMPT creando rol \"%s\";\nCREATE ROLE \"%s\" NOT IDENTIFIED;", this.nombre,this.nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
