package imp.items;

import interfaces.IFItemMigracion;

public class ItemLockUser implements IFItemMigracion {

	protected String userName;
	@Override
	public String getCanonicalString() {
		return String.format("PROMPT bloqueando usuario %s\nALTER USER %s ACCOUNT LOCK;", 
				this.userName,this.userName);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
