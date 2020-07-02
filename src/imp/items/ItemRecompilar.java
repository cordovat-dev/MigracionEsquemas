package imp.items;

import interfaces.IFItemMigracion;

public class ItemRecompilar implements IFItemMigracion {

	@Override
	public String getCanonicalString() {
		return "$ORACLE_HOME/rdbms/admin/utlrp.sql\n";
	}

}
