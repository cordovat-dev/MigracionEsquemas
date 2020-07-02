package imp;

import imp.conectores.ItemsMigracionNoEspecificadosException;
import interfaces.IFConectorMigracion;
import interfaces.IFSuiteMigracion;
import interfaces.IFManejadorSalida;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SuiteMigracion implements IFSuiteMigracion {
	
	private Connection con;
	private List<IFConectorMigracion> conectores;
	private File rutaDestino;
	private List<String> itemsExcluir;
	private List<String> itemsIncluir;
	private String rutaSpool;
	protected boolean guardarDML=false;

	public SuiteMigracion(Connection con_) throws SQLException{
		this.con = con_;
		conectores = new ArrayList<IFConectorMigracion>();
	}

	@Override
	public Connection getCon() {
		return this.con;
	}

	@Override
	public List<IFConectorMigracion> getConectores() {
		return conectores;
	}

	@Override
	public void getConectores(List<IFConectorMigracion> lista_) {
		this.conectores = lista_;

	}

	@Override
	public void agregarConector(IFConectorMigracion conector_) {
		this.conectores.add(conector_);
	}

	@Override
	public void generar() throws ArchivoDeSalidaNoEspecificadoException, ProblemaConRutaDestinoException, SQLException, ItemsMigracionNoEspecificadosException {
		int n = 0;
		IFManejadorSalida ms = Fabrica.getInsManejadorSalida();
		ms.setRutaDestino(this.rutaDestino);
		ms.setGuardarDML(this.guardarDML);
		
		for (IFConectorMigracion cm: this.conectores){		
			cm.setItemsIncluir(this.itemsIncluir);
			cm.setItemsExcluir(this.itemsExcluir);
			ms.setNumeroPaso(++n);	
			ms.setNombre(cm.getNombre());
			ms.setRutaSpool(this.rutaSpool);			
			cm.setManejadorSalida(ms);			
			System.out.println("Generando scripts de: " + cm.getNombre());
			cm.guardar();
			System.out.println();
		}

	}

	@Override
	public void setRutaDestino(String ruta_) throws ProblemaConRutaDestinoException {
		File f = new File(ruta_);
		if (f.exists() && f.isDirectory()){
			this.rutaDestino = f;
		} else {
			throw new ProblemaConRutaDestinoException(ruta_);
		}
	}

	@Override
	public File getRutaDestino() throws ProblemaConRutaDestinoException {
		if (this.rutaDestino == null){throw new ProblemaConRutaDestinoException("ruta nula");}
		return this.rutaDestino;
	}

	@Override
	public void setItemsExcluir(List<String> itemsExcluir_) {
		itemsExcluir = itemsExcluir_;
		
	}

	@Override
	public void setItemsIncluir(List<String> itemsIncluir_) {
		itemsIncluir = itemsIncluir_;
		
	}

	@Override
	public void setRutaSpool(String ruta_) {
		this.rutaSpool = ruta_;
		
	}

	public void setGuardarDML(boolean value) { this.guardarDML=value;}
	
	public boolean getGuardarDML(){return this.guardarDML;}	

}
