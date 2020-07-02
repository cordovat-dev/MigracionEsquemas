package imp.conectores;

import imp.Fabrica;
import imp.items.ItemTablespace;
import interfaces.IFItemMigracion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConectorTablespacesOracle9 extends ConectorTablespaces {

	
	public ConectorTablespacesOracle9(Connection con_) {
		super(con_);
	}
	
	@Override
	protected void cargarLista() throws SQLException {
		String sql = this.armarSQL();
		
		this.sql = sql;
		
		PreparedStatement ps = con.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		
		ItemTablespace tbs = null;
		itemsMigracion = new ArrayList<IFItemMigracion>();
		
		while (rs.next()){
			oracle.sql.CLOB clobValue = (oracle.sql.CLOB)rs.getClob(1);
			BufferedReader reader = new BufferedReader(new InputStreamReader(clobValue.getAsciiStream()));
			String read = null;
			StringBuffer buffer = new StringBuffer();
			try {
				while((read = reader.readLine()) != null ) {
					if (read.trim().length() > 0) {
						buffer.append("\n"+read.trim());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
			buffer.append(";");
			tbs = Fabrica.getInsItemTablespaceOracle9(con);
			tbs.setCanonicalString(buffer.toString());
			tbs.setTbsName(rs.getString("tablespace_name"));
			itemsMigracion.add(tbs);
		}
		rs.close();
		ps.close();
		ps = null;
		rs = null;		
	}

	@Override
	protected String armarSQL() {
		String sql="select dbms_metadata.get_ddl('TABLESPACE',tb.tablespace_name) as fuente_ddl, tablespace_name from dba_tablespaces tb \n"+
				"where \n"+
				"	tablespace_name in ( \n"+
				this.armarSQL2()+
				"\n) order by tb.tablespace_name";				
		return sql;
	}	

}
