package com.elecom.crawler.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.elecom.crawler.common.message.SpringMessageResourceMessages;
import com.elecom.crawler.entity.CrawlerProductJob;
import com.elecom.crawler.entity.ProductBaseInfo;
import com.elecom.crawler.service.ProductInfoService;
import com.elecom.crawler.service.ProductSearchService;
import com.elecom.crawler.utils.CommonUtils;

@Controller
@RequestMapping("/")
public class IndexController {
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private SpringMessageResourceMessages messageSource;

	@Autowired
	private ProductInfoService service;

	@Autowired
	private ProductSearchService productSearchService;

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/")
	public String home(Model model) throws Exception {
		return "tables";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/compare")
	public String index(@RequestParam(value = "pno", required = false) String pno, Model model) throws Exception {
		logger.info("pno = " + pno);
		model.addAttribute("pno", pno);
		return "priceCompare";
	}

	/**
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/uploadfile")
	public String uploadFile() throws Exception {
		return "uploadfile";
	}

	/**
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("myFile") MultipartFile file, Model model) throws Exception {
    	if ( !file.isEmpty() ) {
            String fileName = file.getOriginalFilename();
            if (CommonUtils.isCsv(fileName)) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
                    String readLine = null;
                    List<CrawlerProductJob> records = new ArrayList<CrawlerProductJob>();
                    while((readLine = br.readLine()) != null) {
                    	CrawlerProductJob job = new CrawlerProductJob();
                    	job.setCrawler_product_no(readLine.trim());
                    	records.add(job);
                    }
                    productSearchService.importCsvFileToDB(records);
                	model.addAttribute("messageInfo", "success !! import to Db count : " + records.size());
                    
                }
                 catch ( Exception e ) {
                  	model.addAttribute("message", "please upload correct csv file.");
                 }
            }else {
            	model.addAttribute("message", "please upload csv file.");
            }
        } 
        return "uploadfile";
    }

	/**
	 * Handle request to download an Excel document
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam(value = "pno", required = false) String pno, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			File file = ResourceUtils.getFile("classpath:template.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			Sheet sheet = wb.getSheetAt(0);
			List<ProductBaseInfo> productList = service.getProductAllPricesByPno(pno);
			sheet.getRow(3).getCell(1).setCellValue(pno);
			for (ProductBaseInfo productBaseInfo : productList) {
				// 1 yamada
				// 2 rakuten
				// 3 Amazon
				// 4 yodobasi
				double f = Float.parseFloat(productBaseInfo.getProduct_price());
				if ("1".equals(productBaseInfo.getShop_id())) {
					sheet.getRow(3).getCell(3).setCellValue(f);
				} else if ("2".equals(productBaseInfo.getShop_id())) {
					sheet.getRow(4).getCell(3).setCellValue(f);
				} else if ("3".equals(productBaseInfo.getShop_id())) {
					sheet.getRow(5).getCell(3).setCellValue(f);
				} else if ("4".equals(productBaseInfo.getShop_id())) {
					sheet.getRow(6).getCell(3).setCellValue(f);
				}
			}
			sheet.getRow(7).getCell(1).setCellValue("実施日：" + CommonUtils.getCurrentDate());
			
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String((pno + ".xlsx").getBytes(), "iso-8859-1"));

		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

}
