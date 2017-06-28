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

@Service("rakutenUtils")
public class RakutenCrawlerUtils implements CrawlerUtils {

	protected static final Logger logger = LoggerFactory.getLogger(RakutenCrawlerUtils.class);

	public List<Product> crawlerSearchPages(String url, int JobId) {
		logger.info("RakutenCrawlerUtils.crawlerSearchPages start!!!");
		List<Product> list = new ArrayList<Product>();
		logger.info("RakutenCrawlerUtils.crawlerSearchPages end!!!");
		return list;
	}

	public Product crawlerProduct(String productNo) {
		logger.info("RakutenCrawlerUtils.crawlerProduct start!!!");

		Product product = null;
		WebCrawler wc = new WebCrawler();
		WebURL weburl = new WebURL();
		String url = "http://search.rakuten.co.jp/search/inshop-mall/" + productNo + "/-/sid.193217-st.A?x=17";
		weburl.setURL(url);
		try {
			Page page = wc.processPage(weburl);
			if (page.getParseData() instanceof HtmlParseData) {

				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String html = htmlParseData.getHtml();
				Document doc = Jsoup.parse(html);

				Elements productList = doc.select("div#tableSarch>table>tbody>tr");
				for (Element element : productList) {
					// div[class='pName'] Tag
					Elements prodcutInfo = element.select("td>div>a>img");
					String productName = prodcutInfo.attr("alt");
					if (!productName.contains(productNo)) {
						continue;
					}
					
					// prouct link
					Elements productlink = element.select("td>div>a");
					String link = productlink.attr("href");
					// prouct image link
					String imageLink = prodcutInfo.attr("src");
					// price
					Elements productPrice = element.select("td>font[size=-1]");
					String price = productPrice.text();
					if (productPrice.text()!= null && productPrice.text().length() > 0) {
						price = price.replace(",", "").replaceAll("円", "").trim();
					}
					product = new Product();
					product.setProduct_no(productNo);
					product.setProduct_name(productName);
					product.setProduct_price(price);
					product.setProduct_link_url(link);
					product.setProduct_image_url(imageLink);
			        product.setProduct_point("0");
					product.setShop_id(Contants.RAKUTEN_TYPE);
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("RakutenCrawlerUtils.crawlerProduct end!!!");
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
