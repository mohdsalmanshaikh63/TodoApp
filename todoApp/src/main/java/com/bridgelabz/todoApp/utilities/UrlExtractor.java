package com.bridgelabz.todoApp.utilities;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class UrlExtractor {

	public static final String URL_REGEX = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*"
			+ "[-a-zA-Z0-9+&@#/%=~_|]";

	public static Logger logger = Logger.getLogger(UrlExtractor.class);

	public static Set<String> getUrls(String data) throws Exception {

		Pattern pattern = Pattern.compile(URL_REGEX);
		Matcher matcher = pattern.matcher(data);
		Set<String> urlSet = null;
		logger.info("Set of Url:::::::::" + urlSet);
		while (matcher.find()) {
			if (urlSet == null) {
				urlSet = new HashSet<>();
			}
			urlSet.add(data.substring(matcher.start(), matcher.end()));
		}
		return urlSet;
	}
}
