package imp.items;

import interfaces.IFItemMigracion;

public class ItemUnlockUser implements IFItemMigracion {

	protected String userName;
	@Override
	public String getCanonicalString() {
		return String.format("PROMPT desbloqueando usuario %s\nALTER USER %s ACCOUNT UNLOCK;", 
				this.userName,this.userName);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
