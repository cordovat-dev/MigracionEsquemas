package imp;

import imp.creadores.ConectorNoEncontradoException;
import imp.creadores.ListaCreadores;
import interfaces.IFSuiteMigracion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;


public class Main {

	private static void escribirStackTraceADisco(SQLException ex) {
		File tempFile;
		PrintWriter pw;
		try {
			tempFile = File.createTempFile("mig_db_error_", ".log");
			pw = new PrintWriter(tempFile);
			System.out.println("\nERROR: Posible error en nombre de instancia\nEjecución abortada");
			System.out.println("Leer error en archivo: "+tempFile.getCanonicalPath());
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));		
			pw.write(errors.toString());
			pw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws ProblemaConRutaDestinoException 
	 * @throws ArchivoDeSalidaNoEspecificadoException 
	 * @throws ConectorNoEncontradoException 
	 */
	public static void main(String[] args) throws FileNotFoundException, ProblemaConRutaDestinoException, ArchivoDeSalidaNoEspecificadoException, ConectorNoEncontradoException {
		
		Locale.setDefault(Locale.ENGLISH);
		Connection conn=null;
		
		try {
			
			String rutaPar=args[0];
			String rutaArchivoConectoresSoportados=args[1];
			String rutaDest=args[2];
			String passwd=args[3];
		
			System.out.println("Utilitario de Migracion de Esquemas de Oracle. Tulains Córdova. cordovat@gmail.com \n");
			Parfile parametros = new Parfile(rutaPar);	
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
			String strCon = String.format("jdbc:oracle:thin:@%s:%s:%s", parametros.getIp(), parametros.getPuerto(), parametros.getInstancia());			
			conn = DriverManager.getConnection(strCon, parametros.getUsuario(),passwd);

			ListaCreadores l = ListaCreadores.getInstance(conn);
			
			IFSuiteMigracion sm = l.armarSuiteMigracion(rutaArchivoConectoresSoportados, parametros, conn);
			sm.setRutaDestino(rutaDest);
			sm.setRutaSpool(parametros.getRutaSpool());
			sm.setItemsIncluir(parametros.getUsuariosIncluir());
			sm.setItemsExcluir(parametros.getUsuariosExcluir());
			sm.setGuardarDML(parametros.getGuardarDML());
			System.out.println(parametros.getUsuariosIncluir());
			System.out.println(parametros.getUsuariosExcluir());
			System.out.println();
			sm.generar();
		    conn.close();
		} catch (ProblemaConRutaDestinoException ex) {		    
		    System.out.println(ex.getMessage());
		} catch (SQLException ex) {
				switch (ex.getErrorCode()) {
				case 1017:
					System.out.println("ERROR: Clave errada\nEjecucion abortada");
					break;
				case 17002:
					System.out.println("ERROR: Cadena de coneccion errada (IP:Puerto:Instancia)\nEjecucion abortada");
					break;
				case 1031:
					System.out.println("\nERROR: Privilegios insuficientes\nEjecucion abortada");
					break;
				case 0:
					escribirStackTraceADisco(ex);
					break;
				default:
					   System.out.println("["+ex.getErrorCode()+"]");;
				       ex.printStackTrace();
				       try {
							conn.close();					
						} catch (SQLException e) {
							e.printStackTrace();
						}					
				}
		} catch (ParametroFaltanteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ParametroMalFormadoException e) {
			System.out.println("Error en parámetro: " + e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("usage java -jar MigracionEsquema.jar parfile archivo_conectores carpeta_destino psswd_system");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
