package com.auto.myte.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auto.myte.beans.DataTablesViewBean;
import com.auto.myte.entity.ReceiptInfo;
import com.auto.myte.service.ReceiptInfoService;
import com.auto.myte.utils.DataTableUtils;

@RestController
public class RestFulController {
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

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
            fileLac = "java -jar C:\\PMI\\apache-jmeter-3.2\\bin\\ApacheJMeter.jar";//要调用的程序路径
            p = rt.exec(fileLac);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
	}
}
