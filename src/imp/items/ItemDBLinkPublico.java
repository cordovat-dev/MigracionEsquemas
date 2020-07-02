package imp.items;

import interfaces.IFItemMigracion;

public class ItemDBLinkPublico implements IFItemMigracion {
	protected String name;
	protected String userID;
	protected String password;
	protected String host;
	@Override
	public String getCanonicalString() {		

		String s="";
		String p=String.format("PROMPT creando dblink publico \"%s\"\n",this.name);
		if (this.userID == null) {
			s="CREATE PUBLIC DATABASE LINK %s USING %s;";
			s=String.format(s, this.name, this.userID);
		} else if (this.userID.equals("CURRENT_USER")) {
			s="CREATE PUBLIC DATABASE LINK %s CONNECT TO %s USING %s;";
			s=String.format(s, this.name, this.userID, this.host);
		} else {
			s="CREATE PUBLIC DATABASE LINK %s CONNECT TO %s IDENTIFIED BY %s USING '%s';";
			s=String.format(s, this.name, this.userID, this.password, this.host);
	
		}
		return p+s;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

}
