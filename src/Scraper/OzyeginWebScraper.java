package Scraper;

import java.util.List;

import com.jaunt.Element;
import com.jaunt.Node;
import com.jaunt.UserAgent;

public class OzyeginWebScraper {

	public OzyeginWebScraper(String url) {
		
		//TODO
		
		try {
			UserAgent userAgent = new UserAgent();

			userAgent.visit(url);
			System.out.println(userAgent.getLocation().toString());
			Element peopleListElement = userAgent.doc
					.findFirst("<div class= \"people-list\">");
			System.out.println("sizeElement: "
					+ peopleListElement.getChildElements().size());
			System.out.println("sizeNodes: "
					+ peopleListElement.getChildNodes().size());

			List<Element> elements = peopleListElement.findEvery(
					"<div class=\"people-item \">").toList();

			for (Element personBlock : elements) {
				Element personLeft = personBlock
						.findFirst("<div class=\"people-left\">");
				String personName = personLeft.findFirst(
						"<h3 class=\"people-fullname\">").getText();
				System.out.println(personName);
				Element personBio = personLeft
						.findFirst("<div class=\"people-bio\">");
				String personTitle = personBio.findFirst(
						"<h3 class=\"people-title\">").getText();
				System.out.println(personTitle);
				Element personReach = personBlock
						.findFirst("<div class=\"people-reach\">");
				String mail = personReach
						.findFirst("<a class=\"people-mail\">").getText();
				System.out.println(mail.substring(0, mail.indexOf("ozyegin"))
						+ "@ozyegin.edu.tr");
				String phone = personReach.findFirst(
						"<a class=\"people-call\">").getText();
				System.out.println(phone);
				Element personRight = personBlock
						.findFirst("<div class=\"people-right\">");
				List<Element> titles = personRight.findEvery("<dt>").toList();
				List<Element> answers = personRight.findEach("<dd>").toList();
				List<Element> fff = personRight.findEach("<dd|dt>").toList();
				System.out.println("fff:" + fff.size());
				for (Element e : fff) {
					System.out.println(e.getText());
				}
				System.out.println("///////////////");
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
