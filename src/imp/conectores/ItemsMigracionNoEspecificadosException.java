package imp.conectores;

public class ItemsMigracionNoEspecificadosException extends Exception {

	private static final long serialVersionUID = 1L;

	public ItemsMigracionNoEspecificadosException(String nombre) {
		System.out.println("No se asignó lista de items en conector: "+nombre);
	}

}
