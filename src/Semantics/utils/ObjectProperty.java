package Semantics.utils;



import java.util.ArrayList;
import java.util.List;

/**
 * @author sinan.kordemir
 *
 */
public class ObjectProperty
{
	public String name;

	private List<String> domains = new ArrayList<>();

	private List<String> ranges = new ArrayList<>();

	public List<String> getDomains()
	{
		return domains;
	}

	public String getName()
	{
		return name;
	}

	public List<String> getRanges()
	{
		return ranges;
	}

	/**
	 * @param domains
	 *            Domain Classes of the this object proeprty
	 */
	public void setDomains(List<String> domains)
	{
		this.domains = domains;
	}

	/**
	 * @param name
	 *            Object property name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 *
	 * @param ranges
	 *            Range class of the object property (Which classes can be
	 *            assigned as target property)
	 */
	public void setRanges(List<String> ranges)
	{
		this.ranges = ranges;
	}

}
