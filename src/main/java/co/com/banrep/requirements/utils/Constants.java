/**
 * 
 */
package co.com.banrep.requirements.utils;

/**
 * @author usuario
 *
 */
public class Constants {
	
	public static final String PREFIX_UNI = "uni:";
	public static final String FILTER_STRING = "  FILTER (filter_values) }";
	
	public static final String SPARQL_OR = " || ";
	public static final String SPARQL_EQUAL = " = ";
	
	public static final String GET_INDIVIDUALS_ID = "PREFIX uni: <http://www.banrep.com.co/ontology/2019/requirements.owl#> " +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 
            "PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
    		"SELECT DISTINCT ?id ?class " +
            "WHERE { " +
    		"  ?id ?property ?object ; " +
    		"           rdf:type ?class . " +
    		"  FILTER CONTAINS(LCASE(?object), LCASE(\"key_word\")) " +
    		"  FILTER (?class != owl:NamedIndividual && ?class != rdfs:Resource) " + 
    		"}";
	
	public static final String SPARQL_GENERAL_QUERY = "PREFIX uni: <http://www.banrep.com.co/ontology/2019/requirements.owl#> " +
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
			"SELECT DISTINCT ?project_name ?id_requirement ?description ?priority ?class ?first_name ?last_name ?path_document ?evaluated " +
			"WHERE { " +
			"  ?project uni:hasRequirement ?requerimiento ; " +
		    "           uni:project_name ?project_name . " + 
			"  ?requerimiento uni:id_requirement ?id_requirement ; " +
		    "                 uni:requirement_description ?description ; " +
		    "                 uni:priority ?priority ; " +
		    "                 uni:requirement_path ?path_document ; " +
		    "                 uni:hasEvaluated ?evaluated . " +
		    "  OPTIONAL { " +
		    "    ?evaluated uni:checks ?criteria . " +
		    "  } " +
		    "  ?evaluated rdf:type ?class . " +
		    "  ?responsable uni:isResponsableOf ?evaluated ; "+
		    "               uni:first_name ?first_name ; " +
		    "               uni:last_name ?last_name . " +
		    "  FILTER (?class != owl:NamedIndividual && ?class != rdfs:Resource) " ;
	
	public static final String SPARQL_CRITERIAL_DETAIL = "PREFIX uni: <http://www.banrep.com.co/ontology/2019/requirements.owl#> " +
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
			"SELECT DISTINCT ?id_criteria ?description " +
			"WHERE { " +
			"  my_criteria uni:checks ?criteria . " +
			"  ?criteria uni:id_criteria ?id_criteria ; " +
			"            uni:criteria_description ?description . " +
			"} ";
	
}
