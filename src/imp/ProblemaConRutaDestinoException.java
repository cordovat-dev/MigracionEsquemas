package imp;

public class ProblemaConRutaDestinoException extends Exception {

	public ProblemaConRutaDestinoException(String absolutePath) {
		super("Ruta no existe o no es un directorio: " + absolutePath);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
