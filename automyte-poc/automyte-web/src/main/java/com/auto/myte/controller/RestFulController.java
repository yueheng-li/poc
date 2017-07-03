package com.auto.myte.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auto.myte.beans.DataTablesViewBean;
import com.auto.myte.config.PropertiesConfig;
import com.auto.myte.entity.ReceiptInfo;
import com.auto.myte.service.ReceiptInfoService;
import com.auto.myte.utils.DataTableUtils;
import com.auto.myte.utils.FileUtils;

import ch.qos.logback.core.util.FileUtil;

@RestController
public class RestFulController {
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	private ReceiptInfoService service;

	@RequestMapping("/tables")
	public DataTablesViewBean<ReceiptInfo> list(HttpServletRequest request) {
		DataTableUtils du = new DataTableUtils(request, null, null);
//		String start = request.getParameter("start");  
//		String length = request.getParameter("length");  
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
//		List<ReceiptInfo> receiptInfoList = service.getAllReceiptByEid(userDetails.getUsername(), Integer.parseInt(start), Integer.parseInt(length));
		List<ReceiptInfo> receiptInfoList = service.getAllReceiptByEid(userDetails.getUsername());
		Map<String, List> map = new HashMap<>();
		map.put("data", receiptInfoList);
		DataTablesViewBean<ReceiptInfo> dvb = new DataTablesViewBean<ReceiptInfo>();
		dvb.setData(receiptInfoList);
		dvb.setRecordsFiltered(21);
		dvb.setRecordsTotal(21);
		return dvb;
	}

	@RequestMapping("/details")
	public ReceiptInfo details(@RequestParam(value = "id", required = false) String id) {
		ReceiptInfo receiptInfo = service.getReceiptBaseInfo(id);
		return receiptInfo;
	}

	@RequestMapping("/update")
	public Map<String, String> update(@RequestBody ReceiptInfo form) {
		Map<String, String> map = new HashMap<>();
		try {
			form.setStatus("正常処理された。");
			int flg = service.updateReceiptInfoByKey(form);
			if (flg > 0) {
				map.put("status", "0");
				map.put("message", "update is ok");
			} else {
				map.put("status", "1");
				map.put("message", "update is ng");
			}
		} catch (Exception e) {
			map.put("status", "1");
			map.put("message", "update is exception");
			logger.error(e.getMessage());
		}
		return map;
	}

	@RequestMapping("/delete")
	public Map<String, Integer> delete(@RequestParam(value = "id", required = false) String id) {
		Map<String, Integer> map = new HashMap<>();
		int flg = service.deleteByPrimaryKey(id);
		map.put("flg", Integer.valueOf(flg));
		return map;
	}

	@RequestMapping("/run")
	public void run() {
        Runtime rt = Runtime.getRuntime();
        Process p = null;
        String fileLac = "";
        try {
        	String osName = System.getProperty("os.name" ); 
            logger.info(osName);
            fileLac = propertiesConfig.getRunScript();//要调用的程序路径
//            p = rt.exec(new String[]{"/bin/sh","-c", fileLac});
            String [] cmd={"cmd","/C", fileLac}; 
            p = rt.exec(cmd);
            logger.info(p.toString());
            Thread.sleep(1000);
            
//            InputStream stdin = p.getInputStream();
//            InputStreamReader isr = new InputStreamReader(stdin);
//            BufferedReader br = new BufferedReader(isr);
//            String line = null;
//            System.out.println("<output></output>");
//            while ((line = br.readLine()) != null)
//                System.out.println(line);
//            System.out.println("");
//            int exitVal = p.exitValue();
//            System.out.println("Process exitValue: " + exitVal);
//            System.out.println("<output></output>");
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
	}

	/**
	 * Handle request to download an Excel document
	 */
	@RequestMapping(value = "/downloadr", method = RequestMethod.GET)
	public Map<String, String> download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "";

		String fileName = downloadExcel(result);
		fileName = fileName + "@" + this.downloadJar(result);
		result = "ダウンロード成功！";
		Map<String, String> map = new HashMap<>();
		map.put("messageInfo", fileName + " " + result);
		return map;
	}

	private String downloadExcel(String result) throws FileNotFoundException, IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			File file = ResourceUtils.getFile("classpath:template.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			Sheet sheet = wb.getSheetAt(0);
			List<ReceiptInfo> receiptInfoList = service.getAllReceiptByEid(userDetails.getUsername());
			int i = 1;
			int row = 2;
			for (ReceiptInfo receiptInfo : receiptInfoList) {
				// No.
				sheet.getRow(row).getCell(1).setCellValue(i);
				// Receipt type
				sheet.getRow(row).getCell(2).setCellValue(receiptInfo.getCategory_name());
				// Country of Expense
				sheet.getRow(row).getCell(3).setCellValue("ja");
				// CNY
				sheet.getRow(row).getCell(4).setCellValue("Ja");
				// Amount
				sheet.getRow(row).getCell(5).setCellValue(receiptInfo.getAmount());
				// On
				sheet.getRow(row).getCell(6).setCellValue(receiptInfo.getDate());
				// Reason
				String reason = "";
				if ("1".equals(receiptInfo.getCategory_id())) {
					reason = "Client Site/Other<-> Other/Client Site";
				} else if ("2".equals(receiptInfo.getCategory_id())) {
					reason = "Oviertime Meal Allowance";
				}
				sheet.getRow(row).getCell(7).setCellValue(reason);
				i++;
				row++;
			}

			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
			result = "ダウンロード失敗！";
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		
		String fileName = "C:/test/"+userDetails.getUsername() + ".xlsx";
		File file = new File(fileName);
		FileUtil.createMissingParentDirectories(file);
        FileOutputStream fos = new FileOutputStream(file);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(fos);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			result = "ダウンロード失敗！";
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return fileName;
	}
	


	private String downloadJar(String result) throws FileNotFoundException, IOException {

		String fileName = "C:/test/logback.jar";
		if (FileUtils.judeFileExists(new File(fileName))) {
			return fileName;
		}
		try {
			FileUtils.download("http://localhost:8080/images/automyte.jar", fileName);
		} catch (Exception e) {
			e.printStackTrace();
			result = "ダウンロード失敗！";
		}
		
		return fileName;
	}
}
