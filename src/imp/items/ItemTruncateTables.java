package imp.items;

import interfaces.IFItemMigracion;

public class ItemTruncateTables implements IFItemMigracion {

	private String owner;
	private String nombreTabla;
	
	@Override
	public String getCanonicalString() {
		return String.format("PROMPT truncando tabla \"%s\".\"%s\"\nTRUNCATE TABLE \"%s\".\"%s\";", 
				this.owner, this.nombreTabla, this.owner, this.nombreTabla);
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

}
