package app.crawler.dynasty;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import app.crawler.base.BaseWebsiteCrawler;
import app.crawler.base.ICrawler;
import app.history.dynasty.Dynasty;


public class DynastyCrawler extends BaseWebsiteCrawler implements ICrawler {

	public DynastyCrawler()	{
		super("https://nguoikesu.com/dong-lich-su/hong-bang-va-van-lang", "src/app/data/json");
	}
	
	/** hàm tìm thời gian tồn tại triều đại.
	 * 
	 * @param link
	 * @return String.
	 */
	public String findExitedTime(String link)
	{
		String time = ""; // tạo 1 biến time lưu kết quả crawl.
		// việc crawl có thể thành công hoặc không nên cần đặt trong try catch.
		try
		{
			Document ggInfo = Jsoup.connect(link).get(); // kết nối với trang web và lấy file html về.
        	String exitedTime = ggInfo.select("span.hgKElc > b").text(); // chọn thẻ và lấy text thẻ đó.
        	// nếu không tìm được thông tin gì thì trả về chưa có dữ liệu.
        	if (exitedTime.equals(""))
        	{
        		time = "chưa có dữ liệu";
        	}
        	// ngược lại thì lưu vào time.
        	else
        	{
        		time = exitedTime;
        	}
		}
		catch (IOException e)
		{
			
		}
		return time;
	}
	
	/** hàm tìm các vị vua của từng triều đại.
	 * 
	 * @param link
	 * @return List<String>
	 */
	public List <String> findKing(String link)
	{
		List<String> kingName = new ArrayList <> (); // tạo list lưu kết 
		Document docPage;
		try {
			docPage = Jsoup.connect(link).get(); // kết nối với link và lấy thông tin.
			Elements h2Info = docPage.select("h2[itemprop=name]");
			// chọn lấy thông tin từ thẻ h2 và có [itemprop=name].
			// dùng vòng for đọc thông tin.
	    	for (int i = 0; i < h2Info.size(); i++)
	    	{
	    		String originalString = h2Info.get(i).text();
	    		// nếu xâu có định dạng a - b thì tách và lưu trữ 2 xâu a và b
	    		// ví dụ: Triệu Vũ Vương - Triệu Đà 
	    		if (originalString.contains("-")) {
	    		    String[] parts = originalString.split("-");
	    		    String part1 = parts[0].trim(); 
	    		    String part2 = parts[1].trim(); 
	    		    part2 = part2.replaceFirst("^\\s+", ""); 
	    		    // xoá mấy dấu cách thừa đầu xâu 2 bằng cái này.
	    		    kingName.add(part1);
	    		    kingName.add(part2);
	    		}
	    		else
	    		{
	    			// ngược lại thì lưu luôn xâu.
	    			kingName.add(originalString);
	    		}
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return kingName;
	}
	
	/**
	 * hàm tìm kinh thành của triều đại.
	 * @param link
	 * @return String.
	 */
	public String findCapital(String link)
	{
		String capital = ""; // tạo xâu chứa kết quả crawl.
		try
    	{ 
			// đọc dữ liệu từ link.
        	Document docPage = Jsoup.connect(link).get();
        	try 
        	{
        		String thuDo = docPage.select("th:contains(Thủ đô)").first().nextElementSibling().text();
        		// docPage.select("th:contains(Thủ đô)") sẽ trả về một danh sách các thẻ <th> trong trang HTML có nội dung chứa chuỗi "Thủ đô".
        		// .first() sẽ trả về phần tử đầu tiên trong danh sách trả về.
        		// .nextElementSibling().text() sẽ trả về nội dung văn bản của phần tử kế tiếp sau phần tử <th> đã tìm được (thông qua phương thức nextElementSibling).
        		capital = thuDo;
        	}
        	catch (NullPointerException e)
        	{
        		// nếu mà docPage.select("th:contains(Thủ đô)").first().nextElementSibling().text(); trả về null 
        		// thì sẽ gán là chưa có dữ liệu.
        		capital = "chưa có dữ liệu";
        	}
        	
    	}
    	catch (IOException e)
    	{
    		// nếu đọc dữ liệu từ link thất bại thì sẽ báo là chưa có dữ liệu.
    		capital = "chưa có dữ liệu";
    	}
		return capital;
	}
	
	/**
	 * hàm tạo và ghi ra file json.
	 */
	public void writeJsonFile(List <Dynasty> dynastyList)
	{
		DynastyCrawler dynastyCrawler = new DynastyCrawler(); 
		// // Chuyển đổi danh sách thành JSON
		Gson gson = new Gson();
		String json = gson.toJson(dynastyList);

		// Ghi JSON vào file
		try (FileWriter writer = new FileWriter(dynastyCrawler.getJsonStoreUrls() + "/dynasty.json")) {
			writer.write(json);
			System.out.println("Successfully wrote JSON to file.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void crawl()
	{
		DynastyCrawler dynastyCrawler = new DynastyCrawler();
		String url = dynastyCrawler.getUrl();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select("#Mod88 > div > div > ul > li > ul > li > a:contains(nhà), #Mod88 > div > div > ul > li > a:contains(nhà)"); 

			List<String> dynastyDataList = new ArrayList<>();
			List <String> dynastyLinkList = new ArrayList<> ();
			List <String> exitedTimeList = new ArrayList <String>();
	        List <String> dynastyNameList = new ArrayList <String> ();
	        List <String> capitalList = new ArrayList <String> ();
	        List <List <String>> kingNameList = new ArrayList <> ();
	       
	        // tìm các triều đại (gồm tên và đường dẫn)
	        for (int j = 0; j < elements.size() - 1; j++) {
	        	if (j == 2 || j == 3 || j == 4 || j == 13) continue;
	        	else
	        	{
	        		dynastyDataList.add(elements.get(j).text());
		            dynastyLinkList.add(elements.get(j).attr("href"));
	        	}          
	           
	        }
	        
	        // hàm để tìm exitedTime .
	        for (int j = 0; j < dynastyDataList.size(); j++) {
	        	String name = dynastyDataList.get(j);
	        	dynastyNameList.add(name);
	        	String ggLink = "https://www.google.com/search?q=" + "thời gian tồn tại của " + name;
	        	exitedTimeList.add(findExitedTime(ggLink));
	        }
	       

	        // tìm vua ứng với từng triều đại.
	        for (int j = 0; j < dynastyDataList.size(); j++) {
	        	String link = "https://nguoikesu.com" + dynastyLinkList.get(j);
	        	kingNameList.add(findKing(link));
	        }
	        
	        // tìm kinh thành ứng với từng triều đại.
	        for (int j = 0; j < dynastyNameList.size(); j++)
	        {
	        	String link = "https://vi.m.wikipedia.org/wiki/" + dynastyNameList.get(j);
	        	capitalList.add(findCapital(link));
	        }
	        
	        // tạo và viết vào file json.
	        List<Dynasty> dataList = new ArrayList<>(); // tạo List chứa các object dynasty.
	     // dùng vòng for để tạo và lưu các dynasty vào list.
			for (int i = 0; i < dynastyNameList.size(); i++) {
				String dynasty = dynastyNameList.get(i);
				String capital = capitalList.get(i);
				List kingL = kingNameList.get(i);
				String time = exitedTimeList.get(i);
				Dynasty dynastiesData = new Dynasty();
				dynastiesData.setCapital(capital);
				dynastiesData.setExitedTime(time);
				dynastiesData.setName(dynasty);
				dynastiesData.setKingNameL(kingL);
				dataList.add(dynastiesData);
			}
	        writeJsonFile(dataList);
	        
			}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
