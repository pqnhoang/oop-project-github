package program.scraping.person;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import program.entity.person.Person;

public class PersonScrapGG extends PersonScrap {

	private Document getGGDoc(String query) {
		String url;
		try {
			url = "https://www.google.com/search?q=" +
					URLEncoder.encode(query, StandardCharsets.UTF_8.toString());

			return Jsoup.connect(url).get();
		} catch (Exception e) {
			return null;
		}
	}

	private String getData(Document docGG) {
		String result = docGG.select("a.FLP8od").text();

		if (result.equals("")) {
			result = docGG.select("span.hgKElc > b").text();
			if (result.equals("")) {
				result = "Không rõ";
			}
		}

		return result;
	}

	@Override
	public void scrap() throws IOException {
		for (int i = 0; i < PersonScrap.list.size(); i++) {
			Person person = PersonScrap.list.get(i);

			if (person.getFather().equals("Không rõ")) {
				Document docGG = getGGDoc("Cha của \"" + person.getName() + "\"");
				person.setFather(getData(docGG));
			}

			if (person.getDynastyName().equals("Không rõ")) {
				Document docGG = getGGDoc("Triều đại của \"" + person.getName() + "\"");
				person.setDynastyName(getData(docGG));
			}
		}
	}
}
