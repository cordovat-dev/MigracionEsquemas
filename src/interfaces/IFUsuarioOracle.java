package interfaces;

public interface IFUsuarioOracle {
	public abstract String getUserName();
	public abstract String getProfile();
	public abstract String getPassword();
	public abstract String getDefaultTablespace();
	public abstract String getTemporaryTablespace();
	public abstract String getLocked();
	public abstract String getPwdExpired();
	public abstract void setUserName(String cadena_);
	public abstract void setProfile(String cadena_);
	public abstract void setPassword(String cadena_);
	public abstract void setDefaultTablespace(String cadena_);
	public abstract void setTemporaryTablespace(String cadena_);	
	public abstract void setLocked(String locked_);
	public abstract void setPwdExpired(String expired_);
	
}
