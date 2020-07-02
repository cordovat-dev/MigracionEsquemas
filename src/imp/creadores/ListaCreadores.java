package imp.creadores;

import imp.Parfile;
import imp.SuiteMigracion;
import interfaces.IFConectorMigracion;
import interfaces.IFSuiteMigracion;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ListaCreadores {
	private static volatile ListaCreadores instancia = null;
	private static Map<String,CreadorArbstracto> creadores = null;
	private Connection con;
	
	private ListaCreadores(Connection con_) throws SQLException {
		this.con = con_;
		creadores = new HashMap<String,CreadorArbstracto>();
			
		creadores.put("CONECTOR_EXPORTS",new CreadorConectorExports());
		if (this.esOracle8(con)) {
			creadores.put("CONECTOR_USERS",new CreadorConectorUsuarios());
			creadores.put("CONECTOR_TABLESPACES",new CreadorConectorTablespaces());
		} else if (this.esOracle11(con)) {
			creadores.put("CONECTOR_USERS",new CreadorConectorUsuariosOracle11());
			creadores.put("CONECTOR_TABLESPACES",new CreadorConectorTablespaces());			
		} else {
			creadores.put("CONECTOR_USERS",new CreadorConectorUsuarios());
			creadores.put("CONECTOR_TABLESPACES",new CreadorConectorTablespacesOracle9());
		}
		
		creadores.put("CONECTOR_PROFILE_FUNCTIONS",new CreadorConectorFuncionesProfiles());
		creadores.put("CONECTOR_PROFILES",new CreadorConectorPerfiles());
		creadores.put("CONECTOR_ALL_PROFILES",new CreadorConectorPerfilesGlobales());
		
		creadores.put("CONECTOR_ROLES",new CreadorConectorRoles());
		creadores.put("CONECTOR_ALL_ROLES",new CreadorConectorRolesGlobales());
		creadores.put("CONECTOR_ROLES_GRANTS",new CreadorConectorGrantsRoles());
		creadores.put("CONECTOR_SYSTEM_PRIVILEGES",new CreadorConectorPrivilegiosSistema());
		creadores.put("CONECTOR_IMPORTS",new CreadorConectorImports());		
		creadores.put("CONECTOR_SYNONYMS",new CreadorConectorSinonimos());
		creadores.put("CONECTOR_OBJECT_PRIVILEGES",new CreadorConectorPermisosObjetos());
		creadores.put("CONECTOR_DBLINKS",new CreadorConectorDBLinksPublicos());
		creadores.put("CONECTOR_RECOMPILE_OBJECTS",new CreadorConectorRecompilar());
		creadores.put("CONECTOR_DISABLE_TRIGGERS", new CreadorConectorDisableTriggers());
		creadores.put("CONECTOR_ENABLE_TRIGGERS", new CreadorConectorEnableTriggers());
		creadores.put("CONECTOR_DISABLE_CONSTRAINTS", new CreadorConectorDisableConstraints());
		creadores.put("CONECTOR_ENABLE_CONSTRAINTS", new CreadorConectorEnableConstraints());
		creadores.put("CONECTOR_LOCK_ALL_USERS", new CreadorConectorLockAllUsers());
		creadores.put("CONECTOR_UNLOCK_ALL_USERS", new CreadorConectorUnlockAllUsers());
		creadores.put("CONECTOR_LOCK_USERS", new CreadorConectorLockUsers());
		creadores.put("CONECTOR_UNLOCK_USERS", new CreadorConectorUnlockUsers());
		creadores.put("CONECTOR_TRUNCATE_TABLES", new CreadorConectorTruncateTables());
		creadores.put("CONECTOR_REBUILD_INDEXES", new CreadorConectorRebuildIndexes());
		creadores.put("CONECTOR_CHANGE_PASSWORDS", new CreadorConectorChangePasswords());		
				
	}
	
	public static ListaCreadores getInstance(Connection con_) throws SQLException{
        if (instancia == null) {
            synchronized (ListaCreadores .class){
                    if (instancia == null) {
                    	instancia = new ListaCreadores (con_);
                    }
          }
        }
        return instancia;
	}
	
	public boolean esOracle8(Connection con) throws SQLException{
		boolean retorno=false;
		String sql = "select version from product_component_version t where t.product like 'Oracle%' and t.product like '%Edition%' and t.version like '8%'";
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		
		retorno = rs.next();
		rs.close();
		ps.close();
		ps = null;
		rs = null;	
		return retorno;
	}
	
	public boolean esOracle11(Connection con) throws SQLException{
		boolean retorno=false;
		String sql = "select version from product_component_version t where t.product like 'Oracle%' and t.product like '%Edition%' and t.version like '11%'";
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		
		retorno = rs.next();
		rs.close();
		ps.close();
		ps = null;
		rs = null;	
		return retorno;
	}
	
	public IFSuiteMigracion armarSuiteMigracion(String pathArchivoConectoresSoportados, Parfile parfile, Connection con) throws SQLException, FileNotFoundException, ConectorNoEncontradoException{
		
		SuiteMigracion sm = new SuiteMigracion(con);
		int cont=0;
		
		File archivoConectoresSoportados = new File(pathArchivoConectoresSoportados);
		Scanner scanner = new Scanner(archivoConectoresSoportados);
		while (scanner.hasNextLine()) {
			cont++;
			String nombreConectorSoportado = scanner.nextLine();
			if (nombreConectorSoportado.matches("^CONECTOR_.*$")) {
				try{
					IFConectorMigracion c = creadores.get(nombreConectorSoportado).crearConector(con);
					if (parfile.existeParametro(nombreConectorSoportado)) { sm.agregarConector(c); }					
				} catch (NullPointerException ex){
					throw new ConectorNoEncontradoException(nombreConectorSoportado + "( linea " + cont +" en " + pathArchivoConectoresSoportados + ")");
				}

			} 
		}
		
		return sm;
		
	}


}
