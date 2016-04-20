package com.zhizu.crawler.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by lixin on 14-7-6.
 */
public class HttpParser {

	public static String getValueFromInputByName(String html, String name) {

		Document doc = Jsoup.parse(html);

		String inputValue = doc.select("input[name=" + name + "]").first()
				.attr("value").trim();

		return inputValue;
	}

	/**
	 * 获取html源码中所有hidden
	 * 
	 * @author liuheli
	 *
	 * @param html
	 * @return
	 */
	public static Map<String, Object> getAllHiddenFromInput(String html) {
		Map<String, Object> returnmap = new HashMap<String, Object>();
		Document doc = Jsoup.parse(html);
		Elements hiddens = doc.select("input[type=hidden]");
		for (Element element : hiddens) {
			returnmap.put(element.attr("name"), element.attr("value"));
		}

		return returnmap;
	}

	public static String getValueFromStartInputByName(String html,
			String start, String name) {
		try {
			Document doc = Jsoup.parse(html);

			String inputValue = doc.select(start)
					.select("input[name=" + name + "]").first().attr("value")
					.trim();

			return inputValue;

		} catch (Exception e) {
			System.err.println("name:" + name);
			e.printStackTrace();
			return "";
		}

	}

	public static void main(String args[]) {

		// String gvfdcname = new
		// HttpParser().getValueFromInputByName("<input type=\"hidden\" id=\"gvfdc\" name=\"gvfdcname\" value=\"10\">",
		// "gvfdcname");
		// System.out.println(gvfdcname);
		// String url =
		// "<a class=\"page-end\" href=\"https://consumeprod.alipay.com/record/standard.htm?_input_charset=utf-8&amp;dateRange=oneYear&amp;tradeType=all&amp;status=all&amp;fundFlow=all&amp;beginTime=00%3A00&amp;endDate=2014.11.26&amp;dateType=createDate&amp;beginDate=2013.12.01&amp;endTime=24%3A00&amp;pageNum=8\">βҳ&gt;&gt;</a>";
		// Document doc = Jsoup.parse(url);
		// Elements endPage = doc.select("a.page-end");
		//
		// for (Element element : endPage) {
		// System.out.println(element.attr("href"));
		// }
		try {
			File file = new File("D:/question.html");
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			StringBuffer html = new StringBuffer();
			while ((str = in.readLine()) != null) {
				html.append(str.toString());
			}
			in.close();
			getAllHiddenFromInput(html.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * System.out.println(endPage);
		 * 
		 * Elements elements = doc.select("a"); for(Element element : elements){
		 * System.out.println(element.attr("href")); String
		 * aa=element.attr("href"); int st=aa.indexOf("pageNum=")+8;
		 * System.out.println(st); String bb = aa.substring(st);
		 * System.out.println(bb); }
		 */
	}
}
