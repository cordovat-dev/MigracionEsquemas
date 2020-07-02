package imp.items;


public class ItemClaveUsuario extends ItemUsuario {
	@Override
	public String getCanonicalString() {
		String s = 
		"alter user %s identified by VALUES '%s';\nprompt usuario %s alterado\n";
		s = String.format(s, this.userName, this.password, this.userName);
		return s;
	}	
}
