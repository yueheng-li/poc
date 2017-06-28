package com.elecom.crawler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elecom.crawler.entity.ProductBaseInfo;
import com.elecom.crawler.service.ProductInfoService;

@RestController
public class RestFulController {

    @Autowired
    private ProductInfoService service;

	@RequestMapping("/tables")
	public Map<String, List> hello() {
		
		List<ProductBaseInfo> productList = service.getAllProduct();
		Map<String, List> map = new HashMap<>();
		map.put("data", productList);
		return map;
	}

	@RequestMapping("/details")
	public List<ProductBaseInfo> details(@RequestParam(value="pno" ,required =false ) String pno) {
		List<ProductBaseInfo> productList = service.getProductAllPricesByPno(pno);
		return productList;
	}

}
