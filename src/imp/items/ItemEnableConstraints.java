package imp.items;

public class ItemEnableConstraints extends ItemDisableConstraints  {

	@Override
	public String getCanonicalString() {
		return String.format("PROMPT activando %s %s de la tabla \"%s\".\"%s\"\nALTER TABLE \"%s\".\"%s\" ENABLE CONSTRAINT %s;", 
				this.tipo, this.nombre, this.owner, this.nombreTabla, this.owner, this.nombreTabla, this.nombre);
	}

}
