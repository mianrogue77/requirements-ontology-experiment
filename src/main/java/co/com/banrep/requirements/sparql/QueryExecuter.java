/**
 * 
 */
package co.com.banrep.requirements.sparql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;

import co.com.banrep.requirements.dto.CriteriaDTO;
import co.com.banrep.requirements.dto.RequirementDTO;
import co.com.banrep.requirements.enums.ClassTypeEnum;
import co.com.banrep.requirements.utils.Constants;

/**
 * @author usuario
 *
 */
public class QueryExecuter {

	private OntModel model;

	private static QueryExecuter INSTANCE;
	
	private static HashMap<String, ClassTypeEnum> CLASS_TYPE_MAP;
	
	static {
		CLASS_TYPE_MAP = new HashMap<String, ClassTypeEnum>();
		
		CLASS_TYPE_MAP.put("uni:CriterioEvaluacion", ClassTypeEnum.CREITERIO_EVALUACION);
		CLASS_TYPE_MAP.put("uni:Ambiguo", ClassTypeEnum.CREITERIO_EVALUACION);
		CLASS_TYPE_MAP.put("uni:Completo", ClassTypeEnum.CREITERIO_EVALUACION);
		CLASS_TYPE_MAP.put("uni:Consistente", ClassTypeEnum.CREITERIO_EVALUACION);
		CLASS_TYPE_MAP.put("uni:ContenidoRequerido", ClassTypeEnum.CREITERIO_EVALUACION);
		CLASS_TYPE_MAP.put("uni:Correcto", ClassTypeEnum.CREITERIO_EVALUACION);
		CLASS_TYPE_MAP.put("uni:Modificable", ClassTypeEnum.CREITERIO_EVALUACION);
		CLASS_TYPE_MAP.put("uni:SeguimientoCambios", ClassTypeEnum.CREITERIO_EVALUACION);
		CLASS_TYPE_MAP.put("uni:Trazable", ClassTypeEnum.CREITERIO_EVALUACION);
		CLASS_TYPE_MAP.put("uni:Verificable", ClassTypeEnum.CREITERIO_EVALUACION);
		
		CLASS_TYPE_MAP.put("uni:Proyecto", ClassTypeEnum.PROYECTO);
		
		CLASS_TYPE_MAP.put("uni:Requerimiento", ClassTypeEnum.REQUERIMIENTO);
		CLASS_TYPE_MAP.put("uni:NoFuncional", ClassTypeEnum.REQUERIMIENTO);
		CLASS_TYPE_MAP.put("uni:DeProducto", ClassTypeEnum.REQUERIMIENTO);
		
		CLASS_TYPE_MAP.put("uni:Responsable", ClassTypeEnum.RESPONSABLE);
		
		CLASS_TYPE_MAP.put("uni:Evaluacion", ClassTypeEnum.EVALUACION);
		CLASS_TYPE_MAP.put("uni:Aprobada", ClassTypeEnum.EVALUACION);
		CLASS_TYPE_MAP.put("uni:Rechazada", ClassTypeEnum.EVALUACION);
	}

	/**
	 * Metodo constructor por defecto de la clase
	 */
	private QueryExecuter() {

	}

	public static QueryExecuter getInstance() {
		if (INSTANCE == null) {
			synchronized (QueryExecuter.class) {
				if (INSTANCE == null) {
					INSTANCE = new QueryExecuter();
				}
			}
		}

		return INSTANCE;
	}

	private ResultSet executeQuery(String queryString) {
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, this.model);

		try {
			ResultSet results = qexec.execSelect() ;
			results = ResultSetFactory.copyResults(results);
			
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qexec.close();
		}

		return null;
	}

	private HashMap<String, String> getIndividualsId(String keyword) {
		HashMap<String, String> individualsId = new HashMap<String, String>();
		
		final String queryString = Constants.GET_INDIVIDUALS_ID.replaceAll("key_word", keyword);
		
		ResultSet results = executeQuery(queryString);
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			
			RDFNode id = soln.get("id");
			String idString = id.toString();
			idString = idString.substring(idString.indexOf("#") + 1);
						
			RDFNode myClass = soln.get("class");
			String classString = myClass.toString();
			classString = classString.substring(classString.indexOf("#") + 1);
			
			if (!individualsId.containsKey(Constants.PREFIX_UNI + idString)) {
				individualsId.put(Constants.PREFIX_UNI + idString, Constants.PREFIX_UNI + classString);
			}
		}
		
		return individualsId;
	}
	
	private HashMap<ClassTypeEnum, ArrayList<String>> getFilterValues(HashMap<String, String> results) {
		HashMap<ClassTypeEnum, ArrayList<String>> filterValues = new HashMap<ClassTypeEnum, ArrayList<String>>();

		if (results.size() > 0) {
			Set<String> myKeys = results.keySet();
			for (String key : myKeys) {
				String myClass = results.get(key);
				ClassTypeEnum classType = CLASS_TYPE_MAP.get(myClass);

				if (!filterValues.containsKey(classType)) {
					filterValues.put(classType, new ArrayList<String>());
				}

				filterValues.get(classType).add(key);
			}
		}

		return filterValues;
	}
	
	private String buildFilter(String columnFilter, ArrayList<String> filterValues) {
		if (filterValues.size() > 0) {
			StringBuilder filter = new StringBuilder();
			
			for (int i = 0; i < filterValues.size(); i++) {
				filter.append(columnFilter);
				filter.append(Constants.SPARQL_EQUAL);
				filter.append(filterValues.get(i));
				
				if (i < (filterValues.size() - 1)) {
					filter.append(Constants.SPARQL_OR);
				}
			}
			
			String filterSentence = Constants.FILTER_STRING.replaceAll("filter_values", filter.toString());
			System.out.println(filterSentence);
			return filterSentence;
		}
		
		return null;
	}
	
	private HashMap<Integer, RequirementDTO> getRequirementsFormated(ResultSet results, HashMap<Integer, RequirementDTO> requirements) {
		while (results.hasNext()) {
			RequirementDTO requirement = new RequirementDTO();

			QuerySolution soln = results.nextSolution();

			RDFNode idRequirement = soln.get("id_requirement");
			String idString = idRequirement.toString();
			Integer id = Integer.parseInt(idString.substring(0, idString.indexOf("^")));
			requirement.setIdRequirement(id);

			if (requirements.containsKey(id)) {
				continue;
			}

			RDFNode projectName = soln.get("project_name");
			requirement.setProjectName(projectName.toString());

			RDFNode description = soln.get("description");
			requirement.setDescription(description.toString());

			RDFNode priority = soln.get("priority");
			String priorityString = priority.toString();
			Integer priorityInteger = Integer.parseInt(priorityString.trim().substring(0, priorityString.indexOf("^")));
			requirement.setPriority(priorityInteger);

			RDFNode myClass = soln.get("class");
			String classString = myClass.toString();
			classString = classString.substring(classString.indexOf("#") + 1);
			requirement.setMyClass(Constants.PREFIX_UNI + classString);

			RDFNode firstName = soln.get("first_name");
			RDFNode lastName = soln.get("last_name");
			requirement.setResponsable(firstName.toString() + " " + lastName.toString());

			RDFNode pathDocument = soln.get("path_document");
			requirement.setPathDocument(pathDocument.toString());

			RDFNode evaluated = soln.get("evaluated");
			String evaluatedString = evaluated.toString();
			evaluatedString = evaluatedString.substring(evaluatedString.indexOf("#") + 1);
			requirement.setEvaluated(Constants.PREFIX_UNI + evaluatedString);

			requirements.put(id, requirement);
		}

		return requirements;
	}
	
	public ArrayList<RequirementDTO> getRequirements(String keyword) {
		HashMap<String, String> results = this.getIndividualsId(keyword);
		HashMap<ClassTypeEnum, ArrayList<String>> filterValues = getFilterValues(results);

		if (filterValues.size() > 0) {
			Set<ClassTypeEnum> keys = filterValues.keySet();
			HashMap<Integer, RequirementDTO> requirements = new HashMap<Integer, RequirementDTO>();

			for (ClassTypeEnum key : keys) {
				String filterSentence = this.buildFilter(key.getColumnFilter(), filterValues.get(key));
				String queryComplete = Constants.SPARQL_GENERAL_QUERY + filterSentence;

				ResultSet resultsRequirements = executeQuery(queryComplete);
				requirements = this.getRequirementsFormated(resultsRequirements, requirements);
			}

			ArrayList<RequirementDTO> requirementsList = new ArrayList<RequirementDTO>();
			requirementsList.addAll(requirements.values());

			return requirementsList;
		}

		return new ArrayList<RequirementDTO>();
	}
	
	public ArrayList<CriteriaDTO> getCriterias(String idCriteria) {
		ArrayList<CriteriaDTO> criterias = new ArrayList<CriteriaDTO>();
		
		if (idCriteria != null && idCriteria.trim().length() > 0) {
			String queryString = Constants.SPARQL_CRITERIAL_DETAIL.replaceAll("my_criteria", idCriteria);
			ResultSet results = executeQuery(queryString);
			
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				CriteriaDTO criteria = new CriteriaDTO();
				
				RDFNode myIdCriteria = soln.get("id_criteria");
				String idCriteriaString = myIdCriteria.toString();
				Integer idCriteriaInteger = Integer.parseInt(idCriteriaString.trim().substring(0, idCriteriaString.indexOf("^")));
				criteria.setIdCriteria(idCriteriaInteger);
				
				RDFNode description = soln.get("description");
				criteria.setDescription(description.toString());
				
				criterias.add(criteria);
			}
		}
		
		return criterias;
	}
	
	public OntModel getModel() {
		return model;
	}

	public void setModel(OntModel model) {
		this.model = model;
	}

}
