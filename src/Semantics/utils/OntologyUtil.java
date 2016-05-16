package Semantics.utils;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * @author sinan.kordemir
 *         <p/>
 *         This class use owlapi library and creates Ontology according to this
 *         library. This class accepts # character as default prefix manager
 */

public class OntologyUtil {

	private OWLDataFactory factory;

	private PrefixManager pm;

	private OWLOntology ontology;

	private String pmString = "#";

	private OWLOntologyManager manager;

	private OWLReasoner reasoner;

	private ShortFormEntityChecker entityChecker;

	private BidirectionalShortFormProviderAdapter bidirectionalShortFormProviderAdapter;

	/**
	 * @param ontologyFile
	 * 
	 * @throws OWLOntologyCreationException
	 */
	public OntologyUtil(File ontologyFile) throws OWLOntologyCreationException {
		initializeOntology(ontologyFile);
	}

	/**
	 * @param fileContent
	 * 
	 * @throws OWLOntologyCreationException
	 */
	public OntologyUtil(String fileContent) throws OWLOntologyCreationException {
		initializeOntology(fileContent);
	}

	/**
	 * @param stream
	 * 
	 * @throws OWLOntologyCreationException
	 */
	public OntologyUtil(InputStream stream) throws OWLOntologyCreationException {
		initializeOntology(stream);
	}

	/**
	 * @param fileContent
	 *            path of the ontology file
	 * 
	 * @throws OWLOntologyCreationException
	 */
	private void initializeOntology(String fileContent)
			throws OWLOntologyCreationException {
		InputStream bstream = new ByteArrayInputStream(fileContent.getBytes());
		this.manager = OWLManager.createOWLOntologyManager();
		this.ontology = this.manager.loadOntologyFromOntologyDocument(bstream);
		IRI ontologyIRI = this.ontology.getOntologyID().getOntologyIRI();
		this.pm = new DefaultPrefixManager(ontologyIRI.toString()
				+ this.pmString);
		this.factory = this.manager.getOWLDataFactory();

		ReasonerFactory factory = new ReasonerFactory();
		this.reasoner = factory.createReasoner(this.ontology);

		Set<OWLOntology> onts = new HashSet<>();
		onts.add(this.ontology);

		DefaultPrefixManager defaultPrefixManager = new DefaultPrefixManager(
				this.pm);
		ShortFormProvider shortFormProvider = new ManchesterOWLSyntaxPrefixNameShortFormProvider(
				defaultPrefixManager);
		this.bidirectionalShortFormProviderAdapter = new BidirectionalShortFormProviderAdapter(
				this.manager, onts, shortFormProvider);
		this.entityChecker = new ShortFormEntityChecker(
				this.bidirectionalShortFormProviderAdapter);

	}

	/**
	 * @param stream
	 *            input stream of the Ontology File
	 * 
	 * @throws OWLOntologyCreationException
	 */
	private void initializeOntology(InputStream stream)
			throws OWLOntologyCreationException {
		this.manager = OWLManager.createOWLOntologyManager();
		this.ontology = this.manager.loadOntologyFromOntologyDocument(stream);
		IRI ontologyIRI = this.ontology.getOntologyID().getOntologyIRI();
		this.pm = new DefaultPrefixManager(ontologyIRI.toString()
				+ this.pmString);
		this.factory = this.manager.getOWLDataFactory();

		ReasonerFactory reasonerFactory = new ReasonerFactory();
		this.reasoner = reasonerFactory.createReasoner(this.ontology);

		Set<OWLOntology> ontologies = new HashSet<>();
		ontologies.add(this.ontology);

		DefaultPrefixManager defaultPrefixManager = new DefaultPrefixManager(
				this.pm);
		ShortFormProvider shortFormProvider = new ManchesterOWLSyntaxPrefixNameShortFormProvider(
				defaultPrefixManager);
		this.bidirectionalShortFormProviderAdapter = new BidirectionalShortFormProviderAdapter(
				this.manager, ontologies, shortFormProvider);
		this.entityChecker = new ShortFormEntityChecker(
				this.bidirectionalShortFormProviderAdapter);
	}

	/** @return */
	public OWLDataFactory getFactory() {
		return this.factory;
	}

	/** @param factory */
	public void setFactory(OWLDataFactory factory) {
		this.factory = factory;
	}

	/** @return */
	public OWLOntology getOntology() {
		return this.ontology;
	}

	/** @param ontology */
	public void setOntology(OWLOntology ontology) {
		this.ontology = ontology;
	}

	/** @return */
	public OWLOntologyManager getManager() {
		return this.manager;
	}

	/** @param manager */
	public void setManager(OWLOntologyManager manager) {
		this.manager = manager;
	}

	/**
	 * @param ontologyFile
	 * 
	 * @throws OWLOntologyCreationException
	 */
	private void initializeOntology(File ontologyFile)
			throws OWLOntologyCreationException {
		this.manager = OWLManager.createOWLOntologyManager();
		this.ontology = this.manager
				.loadOntologyFromOntologyDocument(ontologyFile);
		IRI ontologyIRI = this.ontology.getOntologyID().getOntologyIRI();
		this.pm = new DefaultPrefixManager(ontologyIRI.toString()
				+ this.pmString);
		// IRI documentIRI = manager.getOntologyDocumentIRI(ontology);
		this.factory = this.manager.getOWLDataFactory();

		ReasonerFactory reasonerFactory = new ReasonerFactory();
		this.reasoner = reasonerFactory.createReasoner(this.ontology);
	}

	public OWLObjectProperty getObjectProperty(String prop) {
		return this.factory.getOWLObjectProperty(":" + prop, this.pm);
	}

	public OWLDataProperty getDataProperty(String prop) {
		return this.factory.getOWLDataProperty(":" + prop, this.pm);
	}

	/**
	 * @param inv
	 *            Ontology individual
	 * @param prop
	 *            Object Property Name
	 */
	public Set<OWLIndividual> getObjectPropetyValues(OWLIndividual inv,
			String prop) {
		return inv.getObjectPropertyValues(getObjectProperty(prop),
				this.ontology);
	}

	/**
	 * @param className
	 *            Ontology Class name
	 */
	public OWLClass getClass(String className) {
		return this.factory.getOWLClass(":" + className, this.pm);
	}

	public OWLNamedIndividual getIndividual(String invName) {

		return this.factory.getOWLNamedIndividual(":" + invName, this.pm);
	}

	public OWLClass getOwlClass(IRI iri) {
		OWLClass owlClass = this.factory.getOWLClass(iri);
		return owlClass;
	}

	public String getOwlIndClassName(IRI iri) {
		OWLClass owlClass = getOwlClass(iri);
		return cleanType(owlClass.toString());
	}

	public String getIndClassName(OWLNamedIndividual ind) {
		if (ind != null) {
			Set<OWLClassExpression> types = ind.getTypes(this.ontology);
			for (OWLClassExpression owlClassExpression : types) {
				return cleanType(owlClassExpression.toString());
			}
		}
		return null;
	}

	/**
	 * @param propname
	 *            Data property Name
	 * @param propvalue
	 *            Data property value
	 * @param inv
	 *            Individual of which data property will add
	 */
	public void addDataProperty(String propname, String propvalue,
			OWLNamedIndividual inv) {
		OWLAxiom axiom = this.factory.getOWLDataPropertyAssertionAxiom(
				getDataProperty(propname), inv, propvalue);
		this.manager.addAxiom(this.ontology, axiom);
	}

	/**
	 * @param propname
	 *            Data property Name
	 * @param propvalue
	 *            Value of Data Property as boolean
	 * @param inv
	 *            Individual of which data property will add
	 */
	public void addDataProperty(String propname, boolean propvalue,
			OWLNamedIndividual inv) {
		OWLAxiom axiom = this.factory.getOWLDataPropertyAssertionAxiom(
				getDataProperty(propname), inv, propvalue);
		this.manager.addAxiom(this.ontology, axiom);
	}

	/**
	 * @param propname
	 *            Object property name
	 * @param prop
	 *            The property individual which will be added to object
	 * @param obj
	 *            The individual which will have the object property
	 */
	public void addObjectProperty(String propname, OWLNamedIndividual prop,
			OWLNamedIndividual obj) {
		OWLObjectPropertyAssertionAxiom axiom = this.factory
				.getOWLObjectPropertyAssertionAxiom(
						getObjectProperty(propname), obj, prop);
		this.manager.addAxiom(this.ontology, axiom);
	}

	/**
	 * @param invName
	 * @param propName
	 * 
	 * @return
	 */
	public String getNamedInvidualDataProperty(String invName, String propName) {
		String result = null;
		OWLNamedIndividual inv = getIndividual(invName);
		Set<OWLLiteral> propvalues = inv.getDataPropertyValues(
				getDataProperty(propName), this.ontology);
		if (propvalues.size() == 1) {
			OWLLiteral propval = (OWLLiteral) propvalues.toArray()[0];
			result = propval.getLiteral();
		}
		return result;
	}

	/**
	 * @param cls
	 * @param invname
	 * 
	 * @return
	 */
	public OWLNamedIndividual createInvidual(String cls, String invname) {
		OWLNamedIndividual res = this.factory.getOWLNamedIndividual(":"
				+ invname, this.pm);
		this.manager.addAxiom(this.ontology,
				this.factory.getOWLDeclarationAxiom(res));
		OWLClassAssertionAxiom axiom = this.factory.getOWLClassAssertionAxiom(
				getClass(cls), res);
		this.manager.addAxiom(this.ontology, axiom);
		return res;
	}

	/**
	 * @param owlNamedIndividual
	 * 
	 * @return HashMap of data properties
	 */
	public HashMap<String, String> getDataProperties(
			OWLNamedIndividual owlNamedIndividual) {
		HashMap<String, String> dataProperties = new HashMap<>();
		Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = owlNamedIndividual
				.getDataPropertyValues(this.ontology);
		Set<OWLDataPropertyExpression> keySet = dataPropertyValues.keySet();
		for (OWLDataPropertyExpression owlDataPropertyExpression : keySet) {
			Set<OWLLiteral> set = dataPropertyValues
					.get(owlDataPropertyExpression);
			for (OWLLiteral owlLiteral : set) {
				String cleanDataType = cleanType(owlDataPropertyExpression
						.toString());
				dataProperties.put(cleanDataType, owlLiteral.getLiteral());
			}
		}
		return dataProperties;
	}

	/**
	 * A data property can be have multiple values Eg : address = 1.1.1.1 ,
	 * address = HHAA856:34H so address property will have 2 values And this
	 * method returns all the list as HashMap
	 * 
	 * @param owlNamedIndividual
	 * 
	 * @return HashMap of data properties
	 */
	public HashMap<String, List<String>> getDataPropertiesWithList(
			OWLNamedIndividual owlNamedIndividual) {
		HashMap<String, List<String>> dataProperties = new HashMap<>();
		Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues = owlNamedIndividual
				.getDataPropertyValues(this.ontology);
		Set<OWLDataPropertyExpression> keySet = dataPropertyValues.keySet();
		for (OWLDataPropertyExpression owlDataPropertyExpression : keySet) {
			Set<OWLLiteral> set = dataPropertyValues
					.get(owlDataPropertyExpression);
			List<String> list = new ArrayList<>();
			for (OWLLiteral owlLiteral : set) {
				String cleanDataType = cleanType(owlDataPropertyExpression
						.toString());
				list.add(owlLiteral.getLiteral());
				dataProperties.put(cleanDataType, list);
			}
		}
		return dataProperties;
	}

	/**
	 * This method returns all of the individuals and puts them to a HashMap
	 * according to Ontology_Class names
	 * 
	 * @param owlNamedIndividual
	 * 
	 * @return HashMap of Individual List
	 */
	public HashMap<String, List<OWLNamedIndividual>> getObjectProperties(
			OWLNamedIndividual owlNamedIndividual) {

		HashMap<String, List<OWLNamedIndividual>> individualMap = new HashMap<>();
		Set<OWLObjectPropertyAssertionAxiom> objectPropertyAssertionAxioms = this.ontology
				.getObjectPropertyAssertionAxioms(owlNamedIndividual);
		for (OWLObjectPropertyAssertionAxiom owlObjectPropertyAssertionAxiom : objectPropertyAssertionAxioms) {
			OWLNamedIndividual to = (OWLNamedIndividual) owlObjectPropertyAssertionAxiom
					.getObject();
			String cleanIndType = cleanIndType(to);
			List<OWLNamedIndividual> list = individualMap.get(cleanIndType);
			if (list == null) {
				list = new ArrayList<>();
				individualMap.put(cleanIndType, list);
			}
			if (checkIndividual(to, list) == false) {
				list.add(to);
			}
		}
		return individualMap;
	}

	/**
	 * @param ind
	 * @param list
	 * 
	 * @return
	 */
	public boolean checkIndividual(OWLNamedIndividual ind,
			List<OWLNamedIndividual> list) {
		for (OWLNamedIndividual owlNamedIndividual : list) {
			if (owlNamedIndividual.equals(ind)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param expression
	 * 
	 * @return
	 */
	public String cleanType(String expression) {
		String string = expression.toString();
		int indexOf = string.indexOf(this.pmString);
		string = string.substring(indexOf + 1, string.length() - 1);
		return string;
	}

	/**
	 * This method finds the class name of the given individual
	 * 
	 * @param ind
	 * 
	 * @return indivualName as String
	 */
	public String cleanIndType(OWLNamedIndividual ind) {
		String cleanType = null;
		Set<OWLClassExpression> types = ind.getTypes(this.ontology);
		for (OWLClassExpression owlClassExpression : types) {
			cleanType = cleanType(owlClassExpression.toString());
			break;
		}
		return cleanType;
	}

	/**
	 * This method returns the List of individuals according to given
	 * Ontology_Class name
	 * 
	 * @param className
	 * 
	 * @return List of OWLNamedIndividual
	 */
	public List<OWLNamedIndividual> getIndividualsByClassName(String className) {
		List<OWLNamedIndividual> indList = new ArrayList<>();
		Set<OWLNamedIndividual> individualsInSignature = this.ontology
				.getIndividualsInSignature();
		for (OWLNamedIndividual owlNamedIndividual : individualsInSignature) {
			Set<OWLClassExpression> types = owlNamedIndividual
					.getTypes(this.ontology);
			for (OWLClassExpression owlClassExpression : types) {
				if (className.equals(cleanType(owlClassExpression.toString()))) {
					indList.add(owlNamedIndividual);
					break;
				}
			}
		}
		return indList;
	}

	/**
	 * This method returns all the object properties in the ontology with their
	 * domain and ranges
	 * 
	 * @return List of Object properties
	 */
	public List<ObjectProperty> getObjectProperties() {
		List<ObjectProperty> properties = new ArrayList<>();

		Set<OWLObjectProperty> objectPropertiesInSignature = this.ontology
				.getObjectPropertiesInSignature();
		for (OWLObjectProperty owlObjectProperty : objectPropertiesInSignature) {
			ObjectProperty property = new ObjectProperty();
			property.setName(cleanType(owlObjectProperty.toString()));
			Set<OWLClassExpression> ranges2 = owlObjectProperty
					.getRanges(this.ontology);
			for (OWLClassExpression owlClassExpression : ranges2) {
				String cleanType = cleanType(owlClassExpression.toString());
				if (!property.getRanges().contains(cleanType)) {
					property.getRanges().add(
							cleanType(owlClassExpression.toString()));
				}
			}
			Set<OWLClassExpression> domains2 = owlObjectProperty
					.getDomains(this.ontology);
			for (OWLClassExpression owlClassExpression : domains2) {
				Set<OWLClass> classesInSignature = owlClassExpression
						.getClassesInSignature();
				for (OWLClass owlClass : classesInSignature) {
					Set<OWLClassExpression> subClasses = owlClass
							.getSubClasses(this.ontology);
					for (OWLClassExpression owlClassExpression2 : subClasses) {
						String cleanType = cleanType(owlClassExpression2
								.toString());
						if (!property.getRanges().contains(cleanType)) {
							property.getDomains().add(
									cleanType(owlClassExpression2.toString()));
						}
					}
					String cleanType = cleanType(owlClassExpression.toString());
					if (!property.getDomains().contains(cleanType)) {
						property.getDomains().add(
								cleanType(owlClassExpression.toString()));
					}
				}
			}
			properties.add(property);
		}
		return properties;
	}

	/**
	 * This method prints ontology to screen
	 * 
	 * @throws OWLOntologyStorageException
	 */
	public void printOntology() throws OWLOntologyStorageException {
		this.manager.saveOntology(this.ontology, new PrintStream(System.out));
	}

	/**
	 * This method flushes ontology to the given output stream
	 * 
	 * @param stream
	 * 
	 * @throws OWLOntologyStorageException
	 */
	public void saveOntology(OutputStream stream)
			throws OWLOntologyStorageException {
		this.manager.saveOntology(this.ontology, stream);
	}

	/**
	 * This method flushes ontology to the given output file
	 * 
	 * @param fileName
	 * 
	 * @throws OWLOntologyStorageException
	 */
	public void saveOntology(String fileName)
			throws OWLOntologyStorageException, IOException {
		FileUtils.mkdirsExceptLast(fileName);
		try (FileOutputStream out = new FileOutputStream(fileName)) {
			this.manager.saveOntology(this.ontology, out);
		}
	}

	public OWLClassExpression queryOntology(String query)
			throws ParserException {
		;
		ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
				this.manager.getOWLDataFactory(), query);

		parser.setDefaultOntology(this.ontology);

		parser.setOWLEntityChecker(this.entityChecker);

		return parser.parseClassExpression();
	}

	public NodeSet<OWLClass> querySubClasses(String query)
			throws ParserException {
		OWLClassExpression classexpr = queryOntology(query);
		return this.reasoner.getSubClasses(classexpr, false);
	}

	public Set<String> querySubClassesGetClassNames(String query)
			throws ParserException {

		Set<String> classNames = new HashSet<>();
		NodeSet<OWLClass> querySubClasses = querySubClasses(query);
		Iterator<Node<OWLClass>> iterator = querySubClasses.iterator();
		for (Node<OWLClass> node : querySubClasses) {
			Set<OWLClass> entities = node.getEntities();
			for (OWLClass owlClass : entities) {
				String shortForm = this.bidirectionalShortFormProviderAdapter
						.getShortForm(owlClass);
				// String cleanType = cleanType(owlClass.toString());
				classNames.add(shortForm);
			}

		}
		classNames.remove("owl:Nothing");
		return classNames;
	}

	public NodeSet<OWLNamedIndividual> queryInstances(String query)
			throws ParserException {
		OWLClassExpression classexpr = queryOntology(query);
		return this.reasoner.getInstances(classexpr, false);
	}

	public Set getSubClasses(String className) {
		Set<String> classes = new HashSet<>();
		OWLClass class1 = getClass(className);
		NodeSet<OWLClass> subClasses = this.reasoner.getSubClasses(class1,
				false);
		for (Node<OWLClass> node : subClasses) {
			for (OWLClass owlClass : node) {
				String shortForm = this.bidirectionalShortFormProviderAdapter
						.getShortForm(owlClass);
				classes.add(shortForm);
			}
		}
		return classes;
	}
}
