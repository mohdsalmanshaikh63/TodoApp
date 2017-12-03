package com.bridgelabz.todoApp.utilities;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.bridgelabz.todoApp.utilities.pojo.UrlMetaData;

@Component
public class LinkScraper {

	public UrlMetaData getUrlMetaData(String url) throws IOException {

		String title = null;
		String imageUrl = null;

		Document document = Jsoup.connect(url).get();

		Elements metaOgTitle = document.select("meta[property=og:title]");

		// check og title if not availabe check title if not then set to empty string!
		if (metaOgTitle != null) {
			title = metaOgTitle.attr("content");

			if (title == null) {
				title = document.title();

			}
		}

		Elements metaOgImage = document.select("meta[property=og:image]");

		if (metaOgImage != null) {
			imageUrl = metaOgImage.attr("content");			
		}
		
		return new UrlMetaData(title, imageUrl);
	}

}
