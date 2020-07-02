package imp.items;

public class ItemImport extends ItemExport {
	@Override
	public String getCanonicalString() {
		return String.format(
		"imp file=exp%s.dmp log=imp%s.log fromuser=%s touser=%s ignore=y", this.usuario, this.usuario, 
		this.usuario, this.usuario);
	}
}
