package com.elecom.crawler.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.elecom.crawler.common.Contants;
import com.elecom.crawler.common.crawler.WebCrawler;
import com.elecom.crawler.entity.Product;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

@Service("yodobashiUtils")
public class YodobashiCrawlerUtils implements CrawlerUtils {

	protected static final Logger logger = LoggerFactory.getLogger(YodobashiCrawlerUtils.class);

	public List<Product> crawlerSearchPages(String url, int JobId) {
		logger.info("YodobashiCrawlerUtils.crawlerSearchPages start!!!");

		List<Product> list = new ArrayList<Product>();
		WebCrawler wc = new WebCrawler();
		WebURL weburl = new WebURL();
		weburl.setURL(url);
		try {
			Page page = wc.processPage(weburl);
			if (page.getParseData() instanceof HtmlParseData) {

				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String html = htmlParseData.getHtml();
				Document doc = Jsoup.parse(html);

				Elements productList = doc.select("div.pListBlock");
				for (Element element : productList) {
					// div[class='pName'] Tag
					Elements prodcutInfo = element.select("div.pName>p");
					String productNo = "";
					String productName = "";
					if (prodcutInfo.size() > 1) {
						productName = prodcutInfo.get(1).text();
						int index = productName.indexOf("[");
						if (index == -1) {
							productNo = productName;
						} else {
							productNo = productName.substring(0, index).trim();
						}
					}
					// prouct link
					Elements productlink = element.select("a.js_productListPostTag");
					String link = productlink.attr("href");
					// prouct image link
					Elements productImagelink = element.select("div.pImg>img");
					String imageLink = productImagelink.attr("src");
					// price
					Elements productPrice = element.select("span.productPrice");
					String price = "0";
					if (productPrice.text()!= null && productPrice.text().length() > 1) {
						price = productPrice.text().substring(1).replace(",", "");
					}
					// ポイント
					Elements productPoint = element.select("span.orange");
					String ponit = productPoint.text();
					Product product = new Product();
					product.setProduct_no(productNo);
					product.setProduct_name(productName);
					product.setProduct_price(price);
					product.setProduct_link_url(link);
					product.setProduct_image_url(imageLink);
					product.setCrawler_job_id(JobId);
			        String s = "\\d+.\\d+|\\d+";
			        Pattern p = Pattern.compile(s);   
			        Matcher matcher = p.matcher(ponit);  
			        if (matcher.find()) {  
						product.setProduct_point(matcher.group());
			        } else {
			        	product.setProduct_point(ponit);
			        }
					list.add(product);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("YodobashiCrawlerUtils.crawlerSearchPages end!!!");
		return list;
	}

	public Product crawlerProduct(String productNo) {
		logger.info("YodobashiCrawlerUtils.crawlerProduct start!!!");

		Product product = null;
		WebCrawler wc = new WebCrawler();
		WebURL weburl = new WebURL();
		String url = Contants.YODOBASHI_PRODUCT_PAGE + productNo;
		weburl.setURL(url);
		try {
			Page page = wc.processPage(weburl);
			if (page.getParseData() instanceof HtmlParseData) {

				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String html = htmlParseData.getHtml();
				Document doc = Jsoup.parse(html);

				Elements productList = doc.select("div.pListBlock");
				for (Element element : productList) {
					// div[class='pName'] Tag
					Elements prodcutInfo = element.select("div.pName>p");
					String productName = prodcutInfo.text();
					if (!productName.contains(productNo)) {
						continue;
					}
					
					// prouct link
					Elements productlink = element.select("a.js_productListPostTag");
					String link = productlink.attr("href");
					// prouct image link
					Elements productImagelink = element.select("div.pImg>img");
					String imageLink = productImagelink.attr("src");
					// price
					Elements productPrice = element.select("span.productPrice");
					String price = "0";
					if (productPrice.text()!= null && productPrice.text().length() > 1) {
						price = productPrice.text().substring(1).replace(",", "");
					}
					// ポイント
					Elements productPoint = element.select("span.orange");
					String ponit = productPoint.text();
					product = new Product();
					product.setShop_id(Contants.YODOBASHI_TYPE);
					product.setProduct_no(productNo);
					product.setProduct_name(productName);
					product.setProduct_price(price);
					product.setProduct_link_url(link);
					product.setProduct_image_url(imageLink);
			        String s = "\\d+.\\d+|\\d+";
			        Pattern p = Pattern.compile(s);   
			        Matcher matcher = p.matcher(ponit);  
			        if (matcher.find()) {  
						product.setProduct_point(matcher.group());
			        } else {
			        	product.setProduct_point(ponit);
			        }
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("YodobashiCrawlerUtils.crawlerProduct end!!!");
		return product;
	}
	

	private String crawler_job_id;

	public String getCrawler_job_id() {
		return crawler_job_id;
	}


	public void setCrawler_job_id(String crawler_job_id) {
		this.crawler_job_id = crawler_job_id;
	}
	

}
