package interfaces;

import imp.ArchivoDeSalidaNoEspecificadoException;
import imp.ProblemaConRutaDestinoException;
import imp.conectores.ItemsMigracionNoEspecificadosException;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public interface IFManejadorSalida {
	public abstract void setRutaDestino(File rutaDestino_) throws ProblemaConRutaDestinoException;
	public abstract void setGuardarDML(boolean value);
	public abstract void setNumeroPaso(int n);
	public abstract void setNombre(String s);	
	public abstract void setDML(String dml);		
	public abstract void setItemsMigracion(List<IFItemMigracion> items);
	public abstract void setRutaSpool(String rutaSpool);
	public abstract String getRutaSpool();	
	public abstract File getRutaDestino();
	public abstract String getRutaArchivoReporte();
	public abstract String getRutaArchivoLog();	
	public abstract void guardar() throws ArchivoDeSalidaNoEspecificadoException, SQLException, ItemsMigracionNoEspecificadosException;	
	public abstract boolean getGuardarDML();
}
