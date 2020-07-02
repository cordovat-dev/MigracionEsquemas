package imp.items;

import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ItemFuncionProfile implements IFItemMigracion {
	
	protected String nombre;
	protected String lineasCodigo=null;
	protected Connection con;

	public void setLineasCodigo(String lineasCodigo) {
		this.lineasCodigo = lineasCodigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre_) {
		this.nombre = nombre_;
	}

	public ItemFuncionProfile(Connection con_, String nombre_) {
		this.nombre = nombre_;
		this.con = con_;
	}

	@Override
	public String getCanonicalString() {
		if (this.lineasCodigo == null){
			try {
				this.cargarLineasCodigo();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "CREATE OR REPLACE "+this.lineasCodigo;
	}

	private void cargarLineasCodigo() throws SQLException {
		String sql =
		"select \n"+
		"	s.line, \n"+
		"	s.owner, \n"+
		"	s.name, \n"+
		"	substr(s.text,1,length(s.text) - 1) texto \n"+
		"from \n"+
		"	all_source s \n"+
		"where \n"+
		"	type = 'FUNCTION' and \n"+
		"	name = ? and \n"+
		"   OWNER = 'SYS' \n"+
		"order by \n"+
		"	s.line \n";
	
		PreparedStatement ps = con.prepareStatement(sql);	
		ps.setString(1, this.getNombre());
		ResultSet rs = ps.executeQuery();
		this.lineasCodigo = "";
		int i = 0;
		while (rs.next()){

			if (rs.getString("texto") == null) {
				this.lineasCodigo += "\n";
			} else {
				if (++i == 1){
					this.lineasCodigo += rs.getString("texto").replaceFirst(" *\"", " SYS.\"")+"\n";
				} else {
					this.lineasCodigo += rs.getString("texto")+"\n";
				}
			}
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;	
		
	}

}
