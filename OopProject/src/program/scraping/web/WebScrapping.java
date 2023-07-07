package program.scraping.web;

public class WebScrapping {
	String url;
	String jsonStoreUrls;
	String imgStoreUrls;

	public WebScrapping(String url, String jsonStoreUrls, String imgStoreUrls) {
		this.url = url;
		this.imgStoreUrls = imgStoreUrls;
		this.jsonStoreUrls = jsonStoreUrls;
	}

	public WebScrapping(String url, String jsonStoreUrls) {
		this.url = url;
		this.jsonStoreUrls = jsonStoreUrls;
	}

	public String getUrl() {
		return url;
	}

	public String getJsonStoreUrls() {
		return jsonStoreUrls;
	}

	public String getImgStoreUrls() {
		return imgStoreUrls;
	}
}
