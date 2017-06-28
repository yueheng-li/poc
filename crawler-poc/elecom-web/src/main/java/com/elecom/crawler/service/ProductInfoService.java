package com.elecom.crawler.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.elecom.crawler.entity.ProductBaseInfo;
import com.elecom.crawler.mapper.ProductBaseInfoMapper;

@Component
@CacheConfig(cacheNames="ProductInfoService")
public class ProductInfoService {
    private static Logger logger = LoggerFactory.getLogger(ProductInfoService.class);
    @Autowired
    private ProductBaseInfoMapper productInfoMapper;
    
    public ProductBaseInfo getProductBaseInfo() {
        
        ProductBaseInfo info = productInfoMapper.selectByPrimaryKey("EHP-CH1010AGD");
        
        return info;
    }

    @Cacheable
    public List<ProductBaseInfo> getAllProduct() {
    	return productInfoMapper.selectAll();
    }

    public List<ProductBaseInfo> getProductAllPricesByPno(String pno) {
    	return productInfoMapper.selectProductPricesByPno(pno);
    }
}
