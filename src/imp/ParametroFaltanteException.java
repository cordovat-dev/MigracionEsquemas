package imp;

public class ParametroFaltanteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParametroFaltanteException(String param_){
		super("Parámetro " + param_);
	}
}
