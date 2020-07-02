package interfaces;

import imp.ArchivoDeSalidaNoEspecificadoException;
import imp.conectores.ItemsMigracionNoEspecificadosException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface IFConectorMigracion {
	public abstract Connection getCon();
	public abstract void setCon(Connection con);
	public abstract List<IFItemMigracion> getItemsMigracion() throws SQLException;
	public abstract List<String> getItemsExcluir();
	public abstract void setItemsExcluir(List<String> itemsExcluir_);
	public abstract void setItemsExcluir(String[] itemsExcluir_);
	public abstract List<String> getItemsIncluir();
	public abstract void setItemsIncluir(List<String> itemsIncluir);
	public abstract void setItemsIncluir(String[] itemsIncluir);
	public abstract String getNombre();
	public abstract void setManejadorSalida(IFManejadorSalida manejadorSalida) throws SQLException;
	public abstract void guardar() throws ArchivoDeSalidaNoEspecificadoException, SQLException, ItemsMigracionNoEspecificadosException;
}