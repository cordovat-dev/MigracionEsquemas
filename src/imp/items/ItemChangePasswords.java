package imp.items;

import interfaces.IFItemMigracion;

public class ItemChangePasswords implements IFItemMigracion {

	private String userName;
	private String password;

	@Override
	public String getCanonicalString() {
		return String.format("PROMPT cambiando password de usuario \"%s\"\nALTER USER \"%s\" IDENTIFIED BY VALUES '%s';", 
				this.userName, this.userName, this.password);
	}

	public void setUserName(String string) {
		this.userName=string;		
	}

	public void setPassword(String string) {
		this.password=string;		
	}

}
