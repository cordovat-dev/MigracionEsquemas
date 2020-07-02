package imp.items;


import interfaces.IFItemMigracion;
import interfaces.IFUsuarioOracle;

public class ItemUsuario implements IFItemMigracion, IFUsuarioOracle {

	protected String temporaryTablespace;
	protected String defaultTablespace;
	protected String password;
	protected String profile;
	protected String userName;
	protected String locked;
	protected String expired="";
	


	@Override
	public String getCanonicalString() {
		String s = 
		"PROMPT creando usuario \"%s\"\n" +
		"CREATE USER \"%s\" PROFILE \"%s\" \n"+ 
	    "IDENTIFIED BY VALUES '%s' DEFAULT TABLESPACE \"%s\" \n"+ 
	    "TEMPORARY TABLESPACE \"%s\" \n"+
	    "QUOTA UNLIMITED \n"+
	    "ON \"%s\" \n";
		if (!this.expired.equals("-")) {
			s+="PASSWORD EXPIRED \n";
		}
	    s+="ACCOUNT %s;";	    
		s = String.format(s, this.userName, this.userName, this.profile, this.password, 
								    this.defaultTablespace, this.temporaryTablespace, this.defaultTablespace,
								    this.locked
								    );
		return s;
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public String getProfile() {
		return this.profile;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getDefaultTablespace() {
		return this.defaultTablespace;
	}

	@Override
	public String getTemporaryTablespace() {
		return this.temporaryTablespace;
	}

	@Override
	public void setUserName(String cadena_) {
		this.userName=cadena_;
	}

	@Override
	public void setProfile(String cadena_) {
		this.profile = cadena_;
	}

	@Override
	public void setPassword(String cadena_) {
		this.password = cadena_;
	}

	@Override
	public void setDefaultTablespace(String cadena_) {
		this.defaultTablespace = cadena_;
		
	}

	@Override
	public void setTemporaryTablespace(String cadena_) {
		this.temporaryTablespace = cadena_;
		
	}

	@Override
	public String getLocked() {
		return this.locked;
	}

	@Override
	public void setLocked(String locked_) {
		this.locked = locked_;
	}

	@Override
	public String getPwdExpired() {
		return this.expired;
	}

	@Override
	public void setPwdExpired(String expired_) {
		this.expired = expired_;
	}
	
	

}
