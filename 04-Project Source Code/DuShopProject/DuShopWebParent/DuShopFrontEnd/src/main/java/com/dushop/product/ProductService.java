package com.dushop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dushop.common.entity.Product;
/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.product
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-08-12  23:40
 *@Description: TODO
 *@Version: 1.0
 */

@Service
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 10;

    @Autowired private ProductRepository repo;

    public Page<Product> listByCategory(int pageNum, Integer categoryId) {
        String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

        return repo.listByCategory(categoryId, categoryIdMatch, pageable);

    }
}