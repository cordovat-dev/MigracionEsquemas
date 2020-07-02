package imp.conectores;

import imp.Fabrica;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorRecompilar extends ConectorAbstracto {
	
	public ConectorRecompilar() {
		//this.con = con_;
		this.nombre = "RECOMPILAR_OBJETOS";
	}	

	public ConectorRecompilar(Connection con_) {
		this();
	}
	
	@Override
	protected void cargarLista() throws SQLException {
		itemsMigracion = new ArrayList<IFItemMigracion>();
		IFItemMigracion tbs = Fabrica.getInsItemRecompilar();;
		itemsMigracion.add(tbs);

	}

}
