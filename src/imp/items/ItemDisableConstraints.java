package imp.items;

import interfaces.IFItemMigracion;

public class ItemDisableConstraints implements IFItemMigracion {

	protected String owner;
	protected String nombre;
	protected String nombreTabla;
	protected String tipo;
	
	@Override
	public String getCanonicalString() {
		return String.format("PROMPT desactivando %s %s de la tabla \"%s\".\"%s\"\nALTER TABLE \"%s\".\"%s\" DISABLE CONSTRAINT %s;", 
				this.tipo, this.nombre, this.owner, this.nombreTabla, this.owner, this.nombreTabla, this.nombre);
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

	public void setNombreTabla(String nombreTabla_) {
		this.nombreTabla=nombreTabla_;		
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
