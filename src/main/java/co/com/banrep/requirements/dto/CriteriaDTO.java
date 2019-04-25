/**
 * 
 */
package co.com.banrep.requirements.dto;

import java.io.Serializable;

/**
 * @author usuario
 *
 */
public class CriteriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7711231467893575248L;

	private Integer idCriteria;
	private String description;

	public CriteriaDTO() {

	}

	public Integer getIdCriteria() {
		return idCriteria;
	}

	public void setIdCriteria(Integer idCriteria) {
		this.idCriteria = idCriteria;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((idCriteria == null) ? 0 : idCriteria.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CriteriaDTO other = (CriteriaDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (idCriteria == null) {
			if (other.idCriteria != null)
				return false;
		} else if (!idCriteria.equals(other.idCriteria))
			return false;
		return true;
	}

}
