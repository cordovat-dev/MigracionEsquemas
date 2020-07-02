package imp.items;

import interfaces.IFItemMigracion;

public class ItemRebuildIndexes implements IFItemMigracion {

	private String nombre;
	private String owner;

	@Override
	public String getCanonicalString() {
		return String.format("PROMPT reconstruyendo indice \"%s\".\"%s\"\nALTER INDEX \"%s\".\"%s\" REBUILD;", 
				this.owner, this.nombre, this.owner, this.nombre);
	}

	public void setOwner(String owner) {
		this.owner=owner;
	}

	public void setNombre(String nombre) {
		this.nombre=nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public String getOwner() {
		return owner;
	}

}
