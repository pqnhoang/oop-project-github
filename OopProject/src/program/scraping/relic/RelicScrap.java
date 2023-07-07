package program.scraping.relic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import program.entity.relic.Relic;
import program.scraping.web.ScrapException;
import program.scraping.web.WebScrapping;

public class RelicScrap extends WebScrapping implements ScrapException {

	public RelicScrap() {
		super("https://nguoikesu.com", "src/app/data/store/json", "src/app/data/img/relic");
	}

	public String getAddress(String title) {
		Document doc2;
		// Nếu kết nối không thành công sẽ trả về updating
		try {
			doc2 = Jsoup.connect("https://www.google.com/search?q=" + title).get();
		} catch (IOException e) {
			System.out.print(e);
			return "Updating...";
		}
		// Lấy thẻ
		Elements addressTag = doc2.select(
				"#kp-wp-tab-overview > div.TzHB6b.cLjAic.LMRCfc > div > div > div > div > div > div.QsDR1c > div > div > div");

		// Dữ liệu giống nhau sẽ xả ra lỗi -> trả về đang cập nhật (Updating)
		try {
			return addressTag.get(0).text().substring(9);
		} catch (Exception e) {

			return "Upadting";		
		}
	}
	/**
	 * Hàm lấy một danh sách các người liên quan đến di tích lịch sử
	 */
	public List<String> getPersonRelatedRelic(String link) {
		Document doc2;
		List<String> nameList = new ArrayList<String>();
		String name;
		// Nếu kết nối không thành công sẽ trả về rỗng
		try {
			doc2 = Jsoup.connect(getUrl() + link).get();
			// Lấy thẻ
			Elements nameTag = doc2.select("#list-ref-characters > ul > li");
			for (int i = 0; i < nameTag.size(); i++) {
				// lấy thẻ chứa name
				name = nameTag.get(i).text();
				if (name.length() > 1) {
					nameList.add(name);
				}
			}
		} catch (IOException e) {
			return nameList;
		}
		return nameList;
	}

	/**
	 * Hàm lấy title và ghi hoa chữ cái đầu
	 */
	public String getTitleFirstUpperCaseFirstUpperCase(String title) {
		if (title.isEmpty() || title == null)
			return title;
		return title.substring(0, 1).toUpperCase() + title.substring(1);
	}

	/**
	  1 nếu save thành công, 0 nếu không thành công
	 */
	public int getRelicImg(String filename, String link) {
		try {
			// Create a URL object for the image
			URL imgUrl = new URL(getUrl() + link);

			// Read the image from the URL
			BufferedImage image = ImageIO.read(imgUrl);
			File directory = new File(getImgStoreUrls());
			if (!directory.exists()) {
				directory.mkdir();
				System.out.println("Directory created successfully");
			}

			// Create a new file for the new image
			File outputFile = new File(getImgStoreUrls() + "/" + filename);

			// Save the image to the new file
			ImageIO.write(image, "png", outputFile);

		} catch (IOException e) {

			return 0;
		}
		return 1;
	}
	public void saveToJsonFile(List<Relic> relicList) {
		try {
			File directory = new File(getJsonStoreUrls());
			if (!directory.exists()) {
				directory.mkdir();
				System.out.println("Directory created successfully");
			}
			Gson gson = new Gson();

			FileWriter writer = new FileWriter(getJsonStoreUrls() + "/relic.json");
			gson.toJson(relicList, writer);
			writer.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	public void scrap() {
		Document doc1;
		List<Relic> relicList = new ArrayList<Relic>();
		try {
			// Lặp các trang

			for (int k = 0; k <= 20; k += 10) {
				doc1 = Jsoup.connect(getUrl() + "/di-tich-lich-su?start=" + k).get();
				// Lấy các thẻ liên quan đến name
				Elements titles = doc1
						.select("#content > div.com-tags-tag.tag-category > div.com-tags__items > ul > li > h3 > a");

				// lấy các thể liên quan đến content
				Elements contents = doc1
						.select("#content > div.com-tags-tag.tag-category > div.com-tags__items > ul > li > span");

				// lấy các thể liên quan đến content

				Elements imgs = doc1
						.select("#content > div.com-tags-tag.tag-category > div.com-tags__items > ul > li > a > img");

				// lặp mỗi title để lấy ra vì 1 title chỉ có 1 content
				for (int i = 0; i < titles.size(); i++) {
					// get title,content,...
					String title = getTitleFirstUpperCaseFirstUpperCase(titles.get(i).text());
					String href = titles.get(i).attr("href");
					String content = contents.get(i).text();
					// get address
					String address = getAddress(title);
					// get person name
					List<String> nameList = getPersonRelatedRelic(href);
					// get img list relate to this historical site
					String imgUrl = "";

					if (getRelicImg((Relic.cnt + 1) + ".png", imgs.get(i).attr("src")) == 1) {
						imgUrl = (Relic.cnt + 1) + ".png";
					}
					Relic relic = new Relic(title, content, address, nameList, imgUrl);
					if (!relicList.contains(relic))
						relicList.add(relic);
				}
			}
			saveToJsonFile(relicList);
		} catch (IOException e) {
			System.out.print(e);
		}
	}
}
