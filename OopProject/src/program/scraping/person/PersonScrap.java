package program.scraping.person;

import com.google.gson.Gson;

import program.entity.person.Person;
import program.scraping.web.ScrapException;
import program.scraping.web.WebScrapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class PersonScrap extends WebScrapping implements ScrapException {
	public static List<Person> list = new ArrayList<>();

	public PersonScrap() {
		super("https://nguoikesu.com/nhan-vat", "program/app/data/json/person.json",
				"src/app/data/img/person/");
	}

 //exception nếu việc kết nối với web có vấn đề
	public void scrap() throws IOException {
		list.clear();
		new PersonScrapNKS().scrap();
		new PersonWikiCrawl().scrap();
		new PersonScrapGG().scrap();
		saveToJsonFile();
	}

	public void saveToJsonFile() {
		Gson gson = new Gson();
		try (FileWriter file = new FileWriter(getJsonStoreUrls())) {
			file.write(gson.toJson(list));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getPersonImg(String imageUrl, String destinationFile) {
		try {
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();

			BufferedImage img = ImageIO.read(is);
			if (img.getWidth() < 150 || img.getHeight() < 150) {
				System.out.println("Image is too small, skipping...");
				return;
			}

			is.close();
			File outputfile = new File(destinationFile);
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new PersonScrap().scrap();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
