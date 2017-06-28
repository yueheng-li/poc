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

@Service("amazonUtils")
public class AmazonCrawlerUtils implements CrawlerUtils {

	protected static final Logger logger = LoggerFactory.getLogger(AmazonCrawlerUtils.class);

	public List<Product> crawlerSearchPages(String url, int JobId) {
		logger.info("AmazonCrawlerUtils.crawlerSearchPages start!!!");

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

				Elements productList = doc.select("ul#s-results-list-atf>li");
				for (Element element : productList) {
					Elements prodcutInfo = element
							.select("div.a-row.a-spacing-none>span.a-size-small.a-color-secondary"); // div[class='pName']
					String brandName = prodcutInfo.text();
					if (brandName != null && brandName.toUpperCase().contains("ELECOM")) {
						Elements eProName = element.select(
								"div.a-row.a-spacing-mini>div>a.a-link-normal.s-access-detail-page.s-color-twister-title-link.a-text-normal");
						String productName = eProName.attr("title");
						String link = eProName.attr("href");
						String productNo = productName;
						if (productName != null) {
							String[] array = productName.split(" ");

							for (int i = array.length - 1; i > 0; i--) {
								if (CommonUtils.isProductNo(array[i])) {
									productNo = array[i].trim();
									break;
								}
							}
						}

						Elements productPrice = element.select("span.a-size-base.a-color-price.s-price.a-text-bold");
						String price = productPrice.text();
						if (price != null && price.length() > 1) {
							price = productPrice.text().substring(1).replace(",", "");
						}
						// // ポイント
						String ponit = "0";
						String imageLink = element.select("a.a-link-normal.a-text-normal>img").attr("src");

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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AmazonCrawlerUtils.crawlerSearchPages end!!!");
		return list;
	}

	public Product crawlerProduct(String productNo) {
		logger.info("AmazonCrawlerUtils.crawlerProduct start!!!");

		Product product = null;
		WebCrawler wc = new WebCrawler();
		WebURL weburl = new WebURL();
		String url = Contants.AMAZON_PRODUCT_PAGE + productNo;
		weburl.setURL(url);
		try {
			Page page = wc.processPage(weburl);
			if (page.getParseData() instanceof HtmlParseData) {

				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String html = htmlParseData.getHtml();
				Document doc = Jsoup.parse(html);

				Elements productList = doc.select("ul#s-results-list-atf>li");
				for (Element element : productList) {
					Elements prodcutInfo = element
							.select("div.a-row.a-spacing-none>span.a-size-small.a-color-secondary"); // div[class='pName']
					String brandName = prodcutInfo.text();
					if (brandName != null && brandName.toUpperCase().contains("ELECOM")) {
						Elements eProName = element.select(
								"div.a-row.a-spacing-mini>div>a.a-link-normal.s-access-detail-page.s-color-twister-title-link.a-text-normal");
						String productName = eProName.attr("title");
						if (!productName.contains(productNo)) {
							continue;
						}
						String link = eProName.attr("href");

						Elements productPrice = element.select("span.a-size-base.a-color-price.s-price.a-text-bold");
						String price = productPrice.text();
						if (price != null && price.length() > 1) {
							price = productPrice.text().substring(1).replace(",", "");
						}
						// // ポイント
						String ponit = "0";
						String imageLink = element.select("a.a-link-normal.a-text-normal>img").attr("src");

						product = new Product();
						product.setProduct_no(productNo);
						product.setProduct_name(productName);
						product.setProduct_price(price);
						product.setProduct_link_url(link);
						product.setProduct_image_url(imageLink);
						product.setProduct_point(ponit);
						product.setShop_id(Contants.AMAZON_TYPE);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AmazonCrawlerUtils.crawlerProduct end!!!");
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
