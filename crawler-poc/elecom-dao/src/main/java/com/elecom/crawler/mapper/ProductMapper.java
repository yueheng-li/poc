package com.elecom.crawler.mapper;

import com.elecom.crawler.entity.Product;

public interface ProductMapper {

	int insertProduct(Product record);

	Product selectByKey(Product record);

	int updateProductByKey(Product record);
}
