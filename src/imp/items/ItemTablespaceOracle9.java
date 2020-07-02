package imp.items;

import java.sql.Connection;


public class ItemTablespaceOracle9 extends ItemTablespace {

	public ItemTablespaceOracle9(Connection con_) {
		super(con_);
	}
	
	@Override
	public String getCanonicalString() {
		String prompt="PROMPT creando tablespace \""+ this.getTbsName() +"\"";
		return prompt+this.canonicalString;		
	}

	
}
