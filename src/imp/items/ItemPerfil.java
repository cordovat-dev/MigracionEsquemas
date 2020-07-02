package imp.items;

import interfaces.IFItemMigracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ItemPerfil implements IFItemMigracion {
	private String nombre="";
	private List<String> resourcesLimits= new ArrayList<String>();
	private Connection con;
	
	public ItemPerfil(Connection con_) {
		this.setCon(con_);
	}	
	@Override
	public String getCanonicalString() {
		if (this.resourcesLimits.isEmpty()){
			try {
				this.cargarListaResourcesLimits();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		int c = 0;
		String s = String.format("PROMPT creando perfil \"%s\"\n",this.getNombre());
	    s = s + String.format("CREATE PROFILE %s LIMIT \n", this.getNombre());			
		for(String rsrcLimit: this.resourcesLimits){			
			s += rsrcLimit;				
			c++;
			if (c < this.resourcesLimits.size()){
				s+="\n";
			}
		}
		s+=";";			
		
		return s;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<String> getResourcesLimits() {
		return resourcesLimits;
	}
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	}
	
	private void cargarListaResourcesLimits() throws SQLException {
		String sql =
		"select p.resource_name, p.limit from dba_profiles p where p.profile = ? and p.limit<>'DEFAULT' ";
		
		PreparedStatement ps = con.prepareStatement(sql);	
		ps.setString(1, this.getNombre());
		ResultSet rs = ps.executeQuery();
		this.resourcesLimits = new ArrayList<String>();
		while (rs.next()){
			resourcesLimits.add(rs.getString("resource_name")+ " " +rs.getString("limit"));
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;		
		
	}	

}
