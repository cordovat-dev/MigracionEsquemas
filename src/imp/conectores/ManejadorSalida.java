package imp.conectores;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import com.apireportes.rep.Reporte;

import imp.ArchivoDeSalidaNoEspecificadoException;
import imp.ProblemaConRutaDestinoException;
import interfaces.IFItemMigracion;
import interfaces.IFManejadorSalida;

public class ManejadorSalida implements IFManejadorSalida {

	private File rutaDestino;
	private int numeroPaso;
	private String nombre;
	private String rutaSpool;
	private boolean guardarDML;
	protected String copyleft = "-- Utilitario de Migracion de Esquemas de Oracle. Tulains Córdova. cordovat@gmail.com";
	protected String comentario = copyleft + "\n-- SCRIPT GENERADO - %s \n-- PRECAUCION: Recuerde setear la variable de entorno ORACLE_SID";
	private String dml;
	private List<IFItemMigracion> itemsMigracion;
	

	@Override
	public File getRutaDestino() {
		return this.rutaDestino;
	}

	@Override
	public void setRutaDestino(File rutaDestino_) throws ProblemaConRutaDestinoException {
		this.rutaDestino = rutaDestino_;
		if (this.rutaDestino == null){
			throw new ProblemaConRutaDestinoException("null");
		}
		if (!this.rutaDestino.exists() || !this.rutaDestino.isDirectory()){
			throw new ProblemaConRutaDestinoException(this.rutaDestino.getAbsolutePath());
		}
	}

	@Override
	public String getRutaArchivoReporte() {
		if (this.rutaDestino == null) {
			return null;
		}
		String numero = String.format("%02d", this.numeroPaso);
		return this.rutaDestino.getAbsolutePath()+"/"+numero+"_"+this.nombre.toLowerCase()+".sql";	
	}

	@Override
	public String getRutaArchivoLog() {
		String carpeta = "";
		if (this.rutaSpool == null){
			if (this.rutaDestino == null) {
				return null;
			} else {
				carpeta = ".";
			}			
		} else {
			carpeta = this.rutaSpool;
			if (carpeta.endsWith("/")) {
				carpeta = carpeta.substring(0, carpeta.length()-1);
			}
		}

		String numero = String.format("%02d", this.numeroPaso);
		return carpeta+"/"+numero+"_"+this.nombre.toLowerCase()+".log";	
	}

	@Override
	public void setGuardarDML(boolean value) { this.guardarDML=value;}

	@Override
	public boolean getGuardarDML() { return this.guardarDML; }

	@Override
	public void setNumeroPaso(int n) {
		this.numeroPaso=n;
	}

	@Override
	public void setNombre(String s) {
		this.nombre=s;
	}

	@Override
	public void guardar() throws ArchivoDeSalidaNoEspecificadoException,
			SQLException, ItemsMigracionNoEspecificadosException {				
				if (this.itemsMigracion == null) {
					throw new ItemsMigracionNoEspecificadosException(this.nombre);
				}				
				String rutaReporte=this.getRutaArchivoReporte();
				if (rutaReporte == null){throw new ArchivoDeSalidaNoEspecificadoException();}				
				String reporte=String.format(this.comentario, this.nombre)+"\n\n";
				reporte +="spool "+this.getRutaArchivoLog()+";\n\n";
				reporte +="select to_char(sysdate,'dd/mm/yyyy hh24:mi') inicio from dual;\n\n";
				Reporte.escribirDisco("",rutaReporte,false,false); // lo borro primero
				int diezKB = 10240;
				for (IFItemMigracion im: this.itemsMigracion){
					if (im.getCanonicalString().contains("\n")) {
						reporte+=im.getCanonicalString()+"\n\n";
					} else {
						reporte+=im.getCanonicalString()+"\n";
					}
					if (reporte.length() >= diezKB){
						Reporte.escribirDiscoAppend(reporte,rutaReporte);
						reporte="";
					}
				}
				reporte +="select to_char(sysdate,'dd/mm/yyyy hh24:mi') fin from dual;\n\n";
				reporte +="spool off\n";
				Reporte.escribirDisco(reporte,rutaReporte,true,true);
				if (this.guardarDML) {this.guardarDML();};
			}
	
	protected void guardarDML(){
		
		String pathSql = this.getRutaDestino() +"/dml";
		File carpetaDML = new File(pathSql);
		if (!carpetaDML.exists()){
			carpetaDML.mkdir();
		}	
		String numero = String.format("%02d", this.numeroPaso);
		String s = carpetaDML.getAbsolutePath()+"/"+numero+"_"+this.nombre.toLowerCase()+".sql";
		Reporte.escribirDisco(this.copyleft+"\n\n"+this.dml, s, false, false);
	}

	@Override
	public void setDML(String dml) {
		this.dml=dml;
	}

	@Override
	public void setItemsMigracion(List<IFItemMigracion> items) {
		this.itemsMigracion=items;
	}

	@Override
	public void setRutaSpool(String rutaSpool) {
		this.rutaSpool = rutaSpool;
	}

	@Override
	public String getRutaSpool() {
		return this.rutaSpool;
	}	

}
