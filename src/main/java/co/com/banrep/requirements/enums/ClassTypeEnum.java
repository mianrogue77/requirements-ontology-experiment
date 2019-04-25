/**
 * 
 */
package co.com.banrep.requirements.enums;

/**
 * @author usuario
 *
 */
public enum ClassTypeEnum {

	PROYECTO ("?project"),
	REQUERIMIENTO ("?requerimiento"),
	RESPONSABLE ("?responsable"),
	EVALUACION (""),
	CREITERIO_EVALUACION ("?criteria");
	
	private String columnFilter;
	
	ClassTypeEnum(String columnFilter) {
		this.columnFilter = columnFilter;
	}

	public String getColumnFilter() {
		return columnFilter;
	}

	public void setColumnFilter(String columnFilter) {
		this.columnFilter = columnFilter;
	}
	
}
