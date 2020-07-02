package imp.items;

import interfaces.IFItemMigracion;

public class ItemSinonimo implements IFItemMigracion {
	protected String dueno;
	protected String tabla;
	protected String nombre;

	@Override
	public String getCanonicalString() {
		String s1 = String.format("PROMPT creando sinonimo publico \"%s\"\n",this.nombre);
		String s2 = String.format("CREATE PUBLIC SYNONYM \"%s\" FOR \"%s\".\"%s\";", this.nombre, this.dueno, this.tabla);
		return s1+s2;
	}

	public String getDueno() {
		return dueno;
	}

	public void setDueno(String dueno) {
		this.dueno = dueno;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla_) {
		this.tabla = tabla_;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre_) {
		this.nombre = nombre_;
	}

}
