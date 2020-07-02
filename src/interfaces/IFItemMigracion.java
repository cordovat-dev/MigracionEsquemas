package interfaces;

public interface IFItemMigracion {
	public static final long BYTES_EN_KB = 1024;
	public static final long BYTES_EN_MB = BYTES_EN_KB*1024;
	public abstract String getCanonicalString();
}