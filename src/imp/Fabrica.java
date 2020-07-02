package imp;

import imp.conectores.ConectorDBLinksPublicos;
import imp.conectores.ConectorExports;
import imp.conectores.ConectorFuncionesProfiles;
import imp.conectores.ConectorGrantsRoles;
import imp.conectores.ConectorImports;
import imp.conectores.ConectorPerfiles;
import imp.conectores.ConectorPermisosObjetos;
import imp.conectores.ConectorPrivilegiosSistema;
import imp.conectores.ConectorRoles;
import imp.conectores.ConectorSinonimos;
import imp.conectores.ConectorTablespaces;
import imp.conectores.ConectorUsuarios;
import imp.conectores.ManejadorSalida;
import imp.items.ItemChangePasswords;
import imp.items.ItemClaveUsuario;
import imp.items.ItemDBLinkPublico;
import imp.items.ItemDisableTriggers;
import imp.items.ItemEnableTriggers;
import imp.items.ItemExport;
import imp.items.ItemFuncionProfile;
import imp.items.ItemGrantRol;
import imp.items.ItemImport;
import imp.items.ItemPerfil;
import imp.items.ItemPermisoObjeto;
import imp.items.ItemPrivilegioUsuario;
import imp.items.ItemRebuildIndexes;
import imp.items.ItemRecompilar;
import imp.items.ItemRol;
import imp.items.ItemSinonimo;
import imp.items.ItemTablespace;
import imp.items.ItemTablespaceOracle9;
import imp.items.ItemUsuario;
import interfaces.IFConectorMigracion;
import interfaces.IFItemMigracion;
import interfaces.IFSuiteMigracion;
import interfaces.IFManejadorSalida;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Fabrica {

	public static ItemTablespace getInsItemTablespace(Connection con) {
		return new ItemTablespace(con);
	}

	public static IFConectorMigracion getInsConectorTablespaces(Connection con) throws SQLException {
		IFConectorMigracion ct = new ConectorTablespaces(con);
		return ct;
	}

	public static IFConectorMigracion getInsConectorTablespacesLocalAuto(Connection con) throws SQLException {
		ConectorTablespaces ct = new ConectorTablespaces(con);		
		ct.setForzarLocal(true);
		ct.setForzarSegmentAuto(true);
		return ct;
	}	
	
	public static IFSuiteMigracion getInsSuitePrueba(Connection con) throws SQLException {
		SuiteMigracion sm = new SuiteMigracion(con);
		sm.agregarConector(new ConectorPerfiles(con));
		//sm.agregarConector(new ConectorDBLinksPublicos(con));
		sm.agregarConector(new ConectorFuncionesProfiles(con));
		return sm;
	}

	public static ItemUsuario getInsItemUsuario() {
		return new ItemUsuario();
	}
	
	public static List<String> getListaOwnersSystem(){
		List<String> l = new ArrayList<String>();
		l.add("MDSYS");
		l.add("ODM");
		l.add("ODM_MTR");
		l.add("OLAPSYS");
		l.add("ORDPLUGINS");
		l.add("ORDSYS");
		l.add("OUTLN");
		l.add("PUBLIC");
		l.add("SYS");
		l.add("SYSTEM");
		l.add("WKSYS");
		l.add("WMSYS");
		l.add("XDB");
		return l;
		
	}	

	public static ItemClaveUsuario getInsItemClaveUsuario() {
		return new ItemClaveUsuario();
	}

	public static ItemRol getInsItemRol() {
		return new ItemRol();
	}

	public static ItemGrantRol getInsItemRolUsuario() {
		return new ItemGrantRol();
	}

	public static ItemPrivilegioUsuario getInsItemPrivilegioUsuario() {
		return new ItemPrivilegioUsuario();
	}

	public static ItemExport getInsItemExport() {
		return new ItemExport();
	}

	public static ItemImport getInsItemImport() {
		return new ItemImport();
	}

	public static ItemSinonimo getInsItemSinonimo(Connection con) {
		return new ItemSinonimo();
	}

	public static ItemPermisoObjeto getInsItemPermisoObjeto(Connection con) {
		return new ItemPermisoObjeto();
	}

	public static ItemDBLinkPublico getInsItemDBLink() {
		return new ItemDBLinkPublico();
	}

	public static ItemPerfil getInsItemPerfil(Connection con) {
		return new ItemPerfil(con);
	}

	public static ItemFuncionProfile getInsItemFuncionProfile(Connection con, String nombre) {
		return new ItemFuncionProfile(con,nombre);
	}

	public static IFItemMigracion getInsItemRecompilar() {
		return new ItemRecompilar() ;
	}

	public static ItemTablespace getInsItemTablespaceOracle9(Connection con) {
		return new ItemTablespaceOracle9(con);
	}

	public static ItemDisableTriggers getInsItemsDisableAllTriggers() {
		return new ItemDisableTriggers();
	}

	public static ItemEnableTriggers getInsItemsEnableAllTriggers() {
		return new ItemEnableTriggers();
	}

	public static IFManejadorSalida getInsManejadorSalida() {		
		return new ManejadorSalida();
	}

	public static ItemRebuildIndexes getInsItemRebuildIndexes() {
		return new ItemRebuildIndexes();
	}

	public static ItemChangePasswords getInsItemChangePassword() {
		return new ItemChangePasswords() ;
	}


}
