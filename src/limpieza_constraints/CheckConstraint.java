package limpieza_constraints;

public class CheckConstraint implements Comparable<CheckConstraint>{
	private String owner;
	private String name;
	private String tableName;
	private String searchCondition;
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	
	@Override
	public boolean equals(Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CheckConstraint c = (CheckConstraint)o;
		if (
				this.getOwner().equals(c.getOwner()) && 
				this.getTableName().equals(c.getTableName()) &&
				this.getSearchCondition().equals(c.getSearchCondition())
			) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int compareTo(CheckConstraint c) {
		if (c == null ) return -1;
		
		if (this.equals(c)) {return 0;};
		
		String nombreCanonicoThis = this.getOwner()+"."+this.getTableName()+"."+this.getName();
		String nombreCanonicoThat = c.getOwner()+"."+c.getTableName()+"."+c.getName();
		
		return nombreCanonicoThis.compareTo(nombreCanonicoThat);
	}
	
	public String toString(){
		String s = "%s.%s tabla: %s ( %s )";
		
		s = String.format(s, this.getOwner(), this.getName(), this.getTableName(), this.getSearchCondition());
		return s;
	}

	
}
