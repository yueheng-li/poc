package com.elecom.crawler.common.util;

import java.util.ArrayList;
import java.util.List;

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

@Service("yamadaUtils")
public class YamadaCrawlerUtils implements CrawlerUtils {

	protected static final Logger logger = LoggerFactory.getLogger(YamadaCrawlerUtils.class);

	public List<Product> crawlerSearchPages(String url, int JobId) {
		logger.info("YamadaCrawlerUtils.crawlerSearchPages start!!!");

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

				Elements productList = doc.select("div.item-wrapper");
				logger.info("prodcut count : " + productList.size());
				for (Element element : productList) {
					Elements prodcutInfo = element.select("div.item-detail>p.item-name"); // div[class='pName']
																							// Tag
					String productName = prodcutInfo.text();
					String productNo = productName;
					if (productName != null) {
						String[] array = productName.split("　");
						
						for (int i = array.length-1; i > 0; i--) {
							if (CommonUtils.isProductNo(array[i])) {
								productNo = array[i].trim();
								break;
							}
						}
					}
					
					// product link
					String link = "http://www.yamada-denkiweb.com" + prodcutInfo.select("a").attr("href");

					// product image link
					Elements productImagelink = element.select("div.item-thumbnail>a>img");
					String imageLink = productImagelink.attr("src");
					// price
					Elements productPrice = element.select("span.highlight.large");
					String price = productPrice.text();
					if (price != null && price.length() > 1) {
						price = productPrice.text().substring(1).replace(",", "");
					}
					// // ポイント
					Elements productPoint = element.select("span.highlight.point.help");
					String ponit = productPoint.text();

					Product product = new Product();
					product.setProduct_no(productNo);
					product.setProduct_name(productName);
					product.setProduct_price(price);
					product.setProduct_link_url(link);
					product.setProduct_image_url(imageLink);
					product.setCrawler_job_id(JobId);
		        	product.setProduct_point(ponit);
					list.add(product);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("YamadaCrawlerUtils.crawlerSearchPages end!!!");
		return list;
	}

	public Product crawlerProduct(String productNo) {
		logger.info("YamadaCrawlerUtils.crawlerProduct start!!!");

		Product product = null;
		WebCrawler wc = new WebCrawler();
		WebURL weburl = new WebURL();
		String url = Contants.YAMADA_PRODUCT_PAGE + productNo;
		weburl.setURL(url);
		try {
			Page page = wc.processPage(weburl);
			if (page.getParseData() instanceof HtmlParseData) {

				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String html = htmlParseData.getHtml();
				Document doc = Jsoup.parse(html);

				Elements productList = doc.select("div.item-wrapper");
				for (Element element : productList) {
					Elements prodcutInfo = element.select("div.item-detail>p.item-name"); // div[class='pName']
																							// Tag
					String productName = prodcutInfo.text();
					if (!CommonUtils.containsProductNo(productName, productNo)) {
						continue;
					}
					
					// product link
					String link = "http://www.yamada-denkiweb.com" + prodcutInfo.select("a").attr("href");

					// product image link
					Elements productImagelink = element.select("div.item-thumbnail>a>img");
					String imageLink = productImagelink.attr("src");
					// price
					Elements productPrice = element.select("span.highlight.large");
					String price = productPrice.text();
					if (price != null && price.length() > 1) {
						price = productPrice.text().substring(1).replace(",", "");
					}
					// // ポイント
					Elements productPoint = element.select("span.highlight.point.help");
					String ponit = productPoint.text();

					product = new Product();
					product.setProduct_no(productNo);
					product.setProduct_name(productName);
					product.setProduct_price(price);
					product.setProduct_link_url(link);
					product.setProduct_image_url(imageLink);
		        	product.setProduct_point(ponit);
					product.setShop_id(Contants.YAMADA_TYPE);
		        	break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("YamadaCrawlerUtils.crawlerProduct end!!!");
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
