package imp.conectores;

import imp.Fabrica;
import imp.items.ItemImport;
import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConectorImports extends ConectorExports {
	public ConectorImports(Connection con_) {
		super(con_);
		this.nombre = "IMPORTS";
	}

	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();	
		this.sql = sql;
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ItemImport tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		while (rs.next()){
			tbs = Fabrica.getInsItemImport();
			tbs.setUsuario(rs.getString("owner"));
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;
	}
}
