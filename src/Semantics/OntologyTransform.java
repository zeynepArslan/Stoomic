package Semantics;


import Semantics.utils.OntologyUtil;



/**
 * @author sinan.kordemir
 */
public interface OntologyTransform
{
	/**
	 * @return Ontology Util class which will have all the ontology methodes
	 */
	public OntologyUtil getOntologyUtil();

	/**
	 * This method will be implemented by specific transformer classes like
	 * XMLOntologyTransformImp or OntologyDBTransformImp
	 *
	 * @throws Exception
	 */
	public void transform() throws Exception;

}
