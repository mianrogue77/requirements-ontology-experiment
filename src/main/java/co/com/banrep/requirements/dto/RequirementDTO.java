package co.com.banrep.requirements.dto;

import java.io.Serializable;

public class RequirementDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1743013523972056307L;

	private String projectName;
	private Integer idRequirement;
	private String description;
	private Integer priority;
	private String myClass;
	private String responsable;
	private String pathDocument;
	private String evaluated;

	public RequirementDTO() {

	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getIdRequirement() {
		return idRequirement;
	}

	public void setIdRequirement(Integer idRequirement) {
		this.idRequirement = idRequirement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getMyClass() {
		return myClass;
	}

	public void setMyClass(String myClass) {
		this.myClass = myClass;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getPathDocument() {
		return pathDocument;
	}

	public void setPathDocument(String pathDocument) {
		this.pathDocument = pathDocument;
	}

	public String getEvaluated() {
		return evaluated;
	}

	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((evaluated == null) ? 0 : evaluated.hashCode());
		result = prime * result + ((idRequirement == null) ? 0 : idRequirement.hashCode());
		result = prime * result + ((myClass == null) ? 0 : myClass.hashCode());
		result = prime * result + ((pathDocument == null) ? 0 : pathDocument.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((responsable == null) ? 0 : responsable.hashCode());
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
		RequirementDTO other = (RequirementDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (evaluated == null) {
			if (other.evaluated != null)
				return false;
		} else if (!evaluated.equals(other.evaluated))
			return false;
		if (idRequirement == null) {
			if (other.idRequirement != null)
				return false;
		} else if (!idRequirement.equals(other.idRequirement))
			return false;
		if (myClass == null) {
			if (other.myClass != null)
				return false;
		} else if (!myClass.equals(other.myClass))
			return false;
		if (pathDocument == null) {
			if (other.pathDocument != null)
				return false;
		} else if (!pathDocument.equals(other.pathDocument))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		return true;
	}

}
