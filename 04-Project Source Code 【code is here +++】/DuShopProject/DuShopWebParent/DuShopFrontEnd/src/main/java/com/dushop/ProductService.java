package com.dushop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.dushop.common.entity.ProductNotFoundException;
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
    public static final int SEARCH_RESULTS_PER_PAGE = 10;
    @Autowired private ProductRepository repo;

    /*self-code, but adapted from user module*/
    public Page<Product> listByCategory(int pageNum, Integer categoryId) {
        String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

        return repo.listByCategory(categoryId, categoryIdMatch, pageable);
    }

    /*self-code, but adapted from user module*/
    public Product getProduct(String alias) throws ProductNotFoundException {
        Product product = repo.findByAlias(alias);
        if (product == null) {
            throw new ProductNotFoundException("Could not find any product with alias " + alias);
        }

        return product;
    }

    /*self-code, but adapted from user module*/
    public Page<Product> search(String keyword, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
        return repo.search(keyword, pageable);
    }
}