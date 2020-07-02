package limpieza_constraints;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnalizadorCheckConstraints {
	private Connection con;
	private List<CheckConstraint> lista = new ArrayList<CheckConstraint>();
	private List<CheckConstraint> listaDuplicados = new ArrayList<CheckConstraint>();
	private List<CheckConstraint> listaLimpios = new ArrayList<CheckConstraint>();
	
	public AnalizadorCheckConstraints(Connection con_) throws SQLException {
		this.con = con_;
		
		String sql=
			"select \n"+
			"      c.owner, \n"+
			"      c.table_name, \n"+
			"      c.constraint_name, \n"+
			"      c.search_condition \n"+
			"from \n"+
			"    dba_constraints c \n"+	
			"where \n"+
			"     c.constraint_type = 'C' --and \n"+
			"     --c.constraint_name = 'SYS_C0019334'\n";						
				
			
			PreparedStatement ps = con.prepareStatement(sql);		
			ResultSet rs = ps.executeQuery();
			CheckConstraint c = null;
			while (rs.next()){
				c = new CheckConstraint();
				c.setName(rs.getString("constraint_name"));
				c.setOwner(rs.getString("owner"));
				c.setSearchCondition(rs.getString("search_condition"));
				c.setTableName(rs.getString("table_name"));
				this.lista.add(c);
			}
			rs.close();
			ps.close();
			ps = null;
			rs = null;	
			
			this.procesar();
	}

	public List<CheckConstraint> getLista() {
		return lista;
	}
	
	private void procesar(){
		for (CheckConstraint c: lista ) {
			if(this.listaLimpios.contains(c)) {
				this.listaDuplicados.add(c);
			} else {
				this.listaLimpios.add(c);
			}
		}
		
		Collections.sort(this.listaDuplicados);
		Collections.sort(this.listaLimpios);
		Collections.sort(this.lista);
	}

	public List<CheckConstraint> getListaDuplicados() {
		return listaDuplicados;
	}

	public List<CheckConstraint> getListaLimpios() {
		return listaLimpios;
	}
	
	
}
