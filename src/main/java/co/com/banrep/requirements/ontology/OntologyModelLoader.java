/**
 * 
 */
package co.com.banrep.requirements.ontology;

import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * @author usuario
 *
 */
public class OntologyModelLoader {

	private static OntologyModelLoader INSTANCE;

	private OntModel model;

	private OntologyModelLoader() {
		InputStream in = null;
		try {
			String respath = "/owl/requirements.owl";
			in = OntologyModelLoader.class.getResourceAsStream(respath);
			
			if ( in == null )
			    System.out.println("resource not found: " + respath);
			
			this.model = ModelFactory.createOntologyModel();
			this.model.read(in, "RDF/XML");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static OntologyModelLoader getIntance() {
		if (INSTANCE == null) {
			synchronized (OntologyModelLoader.class) {
				if (INSTANCE == null) {
					INSTANCE = new OntologyModelLoader();
				}
			}
		}

		return INSTANCE;
	}

	public OntModel getModel() {
		return model;
	}

	public void setModel(OntModel model) {
		this.model = model;
	}

}
