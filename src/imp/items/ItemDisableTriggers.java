package imp.items;

import interfaces.IFItemMigracion;

public class ItemDisableTriggers implements IFItemMigracion {

	protected String owner;
	protected String nombre;
	protected String ownerTabla;
	protected String nombreTabla;
	
	@Override
	public String getCanonicalString() {
		return String.format("PROMPT desactivando trigger de tabla \"%s\".\"%s\" %s\nALTER TRIGGER %s.%s DISABLE;", 
				this.ownerTabla,this.nombreTabla,this.nombre,this.owner,this.nombre);
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOwnerTabla() {
		return ownerTabla;
	}

	public void setOwnerTabla(String ownerTabla) {
		this.ownerTabla = ownerTabla;
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

}
