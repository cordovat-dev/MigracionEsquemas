package interfaces;

import imp.ArchivoDeSalidaNoEspecificadoException;
import imp.ProblemaConRutaDestinoException;
import imp.conectores.ItemsMigracionNoEspecificadosException;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface IFSuiteMigracion {
	public abstract Connection getCon();
	public abstract List<IFConectorMigracion> getConectores();
	public abstract void getConectores(List<IFConectorMigracion> lista_);
	public abstract void agregarConector(IFConectorMigracion conector_);
	public abstract void generar() throws ArchivoDeSalidaNoEspecificadoException, ProblemaConRutaDestinoException, SQLException, ItemsMigracionNoEspecificadosException;
	public abstract void setRutaDestino(String ruta_) throws ProblemaConRutaDestinoException;
	public abstract File getRutaDestino() throws ProblemaConRutaDestinoException;
	public abstract void setRutaSpool(String ruta_);
	public abstract void setItemsExcluir(List<String> itemsExcluir_);
	public abstract void setItemsIncluir(List<String> itemsIncluir_);
	public abstract void setGuardarDML(boolean value);
	public abstract boolean getGuardarDML();	
}
