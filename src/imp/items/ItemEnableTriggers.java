package imp.items;

public class ItemEnableTriggers extends ItemDisableTriggers {
	@Override
	public String getCanonicalString() {
		return String.format("PROMPT activando trigger de tabla \"%s\".\"%s\" %s\nALTER TRIGGER %s.%s ENABLE;", 
				this.ownerTabla,this.nombreTabla,this.nombre,this.owner,this.nombre);
	}
}
