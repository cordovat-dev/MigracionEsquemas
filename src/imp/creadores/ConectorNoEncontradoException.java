package imp.creadores;

public class ConectorNoEncontradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConectorNoEncontradoException(String conector){
		super("Conector "+conector+" no se encontro");
	}
}
