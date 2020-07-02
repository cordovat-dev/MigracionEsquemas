package imp;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parfile {

	private List<String> usuariosIncluir = new ArrayList<String>();
	private List<String> usuariosExcluir = new ArrayList<String>();
	private String ip="";
	private String puerto="";
	private String instancia="";
	private String usuarioSYS="";
	private String rutaSpool;
	private Properties prop = new Properties();	
	private boolean guardarDML = false;

	private static boolean validarIP(String ip_) {

		Pattern p = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");
		Matcher m = p.matcher(ip_);

		m.find();
		try {
			int[] numeros = new int[]{Integer.parseInt(m.group(1)),Integer.parseInt(m.group(2)),Integer.parseInt(m.group(3)),Integer.parseInt(m.group(4))};

			for (int i=0;i<numeros.length;i++){
				if (numeros[i]>255 ){return false;}
			}
			
		} catch (IllegalStateException ex) {
			return false;
		}
		
		return true;
		
	}
	
	private List<String> parseLista(String lista_){
		List<String> lista = new ArrayList<String>();
		
		if (lista_.equals("")){ return lista;}
		
		String s[] = lista_.split(",");
		
		for (String st : s){
			lista.add(st.trim());
		}
		return lista;
	}
	
	public Parfile(String rutaArchivoConf) throws ParametroFaltanteException, ParametroMalFormadoException, FileNotFoundException, IOException{
		    	    	
    	prop.load(new FileInputStream(rutaArchivoConf));
 
        String stUsuariosIncluir = prop.getProperty("ESQUEMAS_INCLUIR","").trim();
        String stUsuariosExcluir = prop.getProperty("ESQUEMAS_EXCLUIR","").trim();
        String stGuardarDML = prop.getProperty("GENERAR_DML","FALSE").trim();
        this.usuariosIncluir = this.parseLista(stUsuariosIncluir);
        this.usuariosExcluir = this.parseLista(stUsuariosExcluir);
        this.rutaSpool = prop.getProperty("RUTA_SPOOL");
        this.ip = prop.getProperty("IP");
        this.puerto = prop.getProperty("PUERTO");
        this.instancia = prop.getProperty("INSTANCIA");
        this.usuarioSYS = prop.getProperty("USUARIO_SYS");
        this.guardarDML = (stGuardarDML.equals("TRUE"));
        
        if (ip == null){throw new ParametroFaltanteException("IP");}
        if (puerto == null){throw new ParametroFaltanteException("PUERTO");}
        if (instancia == null){throw new ParametroFaltanteException("INSTANCIA");}
        if (usuarioSYS == null){usuarioSYS="FALSE";}

        if (Parfile.validarIP(ip) == false ) {throw new ParametroMalFormadoException("IP="+ip);}
        if (puerto.matches("^([0-9]{1,4})$") == false ) {throw new ParametroMalFormadoException("PUERTO="+puerto);}
        if (instancia.matches("^[a-zA-Z][a-zA-Z0-9_]{2,20}$") == false ) {throw new ParametroMalFormadoException("INSTANCIA="+instancia);}
        if (stGuardarDML.matches("FALSE|TRUE") == false){throw new ParametroMalFormadoException("GENERAR_DML="+stGuardarDML);}
        if (usuarioSYS.matches("FALSE|TRUE") == false){throw new ParametroMalFormadoException("USUARIO_SYS="+usuarioSYS);}        
	}
	
	public List<String> getUsuariosIncluir() {
		return usuariosIncluir;
	}

	public List<String> getUsuariosExcluir() {
		return usuariosExcluir;
	}

	public String getIp() {
		return ip;
	}

	public String getPuerto() {
		return puerto;
	}

	public String getInstancia() {
		return instancia;
	}
	
	public String getRutaSpool() {
		return rutaSpool;
	}
	
	public boolean existeParametro(String clave){
		return this.prop.getProperty(clave)!=null;
	}

	public boolean getGuardarDML() {
		return this.guardarDML;
	}

	public String getUsuarioSYS() {
		return usuarioSYS;
	}
	
	public String getUsuario(){
		if (this.usuarioSYS.equals("TRUE")){
			return "SYS AS SYSDBA";
		} else {
			return "SYSTEM";
		}
	}
}
