package imp.conectores;

import imp.ArchivoDeSalidaNoEspecificadoException;
import interfaces.IFConectorMigracion;
import interfaces.IFItemMigracion;
import interfaces.IFManejadorSalida;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class ConectorAbstracto implements IFConectorMigracion {

	protected Connection con;	
	protected List<String> itemsExcluir = new ArrayList<String>();
	protected List<IFItemMigracion> itemsMigracion;
	protected List<String> itemsIncluir = new ArrayList<String>();
	protected String nombre = "NONAME";
	protected String sql = "";
	protected IFManejadorSalida manejadorSalida;
	
	public String getSql() {
		return sql;
	}

	public static String getCadenaItemsSQL(List<String> l) {
		String retorno="( ";
		int c = 0;
		for (String s: l){
			c++;
			if (c >1){
				retorno += ",'"+s+"'";
			} else {
				retorno += "'"+s+"'";
			}
		}
		return retorno+" )";
		
	}
	
	@Override
	public Connection getCon() {
		return con;
	}

	@Override
	public void setCon(Connection con_) {
		this.con = con_;
	}

	@Override
	public List<IFItemMigracion> getItemsMigracion() throws SQLException {
		if (itemsMigracion == null) {
			this.cargarLista();			
		}
		return itemsMigracion;
	}

	protected abstract void cargarLista() throws SQLException;

	@Override
	public List<String> getItemsExcluir() {
		return itemsExcluir;
	}

	@Override
	public void setItemsExcluir(List<String> itemsExcluir_) {
		itemsExcluir = itemsExcluir_;
	
	}

	@Override
	public void setItemsExcluir(String[] itemsExcluir_) {
		for (String s: itemsExcluir_){
			this.itemsExcluir.add(s);
		}
	
	}

	@Override
	public List<String> getItemsIncluir() {
		return itemsIncluir;
	}

	@Override
	public void setItemsIncluir(List<String> itemsIncluir_) {
		this.itemsIncluir = itemsIncluir_;
	
	}

	@Override
	public void setItemsIncluir(String[] itemsIncluir) {
		for (String s: itemsIncluir){
			this.itemsIncluir.add(s);
		}
	
	}

	public ConectorAbstracto() {
		super();
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public void guardar() throws ArchivoDeSalidaNoEspecificadoException, SQLException, ItemsMigracionNoEspecificadosException{
		this.manejadorSalida.setItemsMigracion(this.itemsMigracion);
		this.manejadorSalida.setDML(this.sql);
		this.manejadorSalida.guardar();
	}

	@Override
	public void setManejadorSalida(IFManejadorSalida manejadorSalida) throws SQLException {
		this.manejadorSalida = manejadorSalida;
		this.manejadorSalida.setItemsMigracion(this.getItemsMigracion());
	}
	

}