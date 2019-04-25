/**
 * 
 */
package co.com.banrep.requirements.view;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import co.com.banrep.requirements.dto.CriteriaDTO;
import co.com.banrep.requirements.dto.RequirementDTO;
import co.com.banrep.requirements.ontology.OntologyModelLoader;
import co.com.banrep.requirements.sparql.QueryExecuter;

/**
 * @author usuario
 *
 */
@ManagedBean
@ViewScoped
public class SearchRequirements implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -744728473879620537L;

	private String keyword = "";
	private RequirementDTO selectedRequirement = null;
	private ArrayList<RequirementDTO> requirements = new ArrayList<RequirementDTO>();
	private ArrayList<CriteriaDTO> criterias = new ArrayList<CriteriaDTO>();

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public RequirementDTO getSelectedRequirement() {
		return selectedRequirement;
	}

	public void setSelectedRequirement(RequirementDTO selectedRequirement) {
		this.selectedRequirement = selectedRequirement;
	}

	public ArrayList<RequirementDTO> getRequirements() {
		return requirements;
	}

	public void setRequirements(ArrayList<RequirementDTO> requirements) {
		this.requirements = requirements;
	}

	public ArrayList<CriteriaDTO> getCriterias() {
		return criterias;
	}

	public void setCriterias(ArrayList<CriteriaDTO> criterias) {
		this.criterias = criterias;
	}

	public String showGreeting() {
		OntologyModelLoader ontoModel = OntologyModelLoader.getIntance();

		QueryExecuter queryExecuter = QueryExecuter.getInstance();
		queryExecuter.setModel(ontoModel.getModel());

		if (keyword != null && keyword.trim().length() > 0) {
//			HashMap<String, String> results = queryExecuter.getIndividualsId(keyword);
//
//			if (results.size() > 0) {
//				for (Map.Entry<String, String> entry : results.entrySet()) {
//					System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
//				}
//			}
			this.requirements = queryExecuter.getRequirements(keyword);
		}

		return "Hello " + this.keyword + "!";
	}
	
	public void obtainCriterias(RequirementDTO rq) {
		OntologyModelLoader ontoModel = OntologyModelLoader.getIntance();

		QueryExecuter queryExecuter = QueryExecuter.getInstance();
		queryExecuter.setModel(ontoModel.getModel());

		if (rq != null) {
			this.criterias = queryExecuter.getCriterias(rq.getEvaluated());
		}
	}
}
