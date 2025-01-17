package com.dushop.common.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.common.entity
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-08-27  16:43
 *@Description: TODO
 *@Version: 1.0
 */


@Entity //加在实体类上, 定义对象将会成为被JPA管理的实体, 将映射到指定的数据库。
@Table(name = "order_details")
public class OrderDetail extends IdBasedEntity {

    /*Private fileds - self-finish*/
    private int quantity;

    /*Private fileds - self-finish*/
    private float productCost;

    /*Private fileds - self-finish*/
    private float shippingCost;

    /*Private fileds - self-finish*/
    private float unitPrice;

    /*Private fileds - self-finish*/
    private float subtotal;

    /*Private fileds - self-finish*/
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /*Private fileds - self-finish*/
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /////////////////////////////////////////////////
    /*Following parts is Auto-generated by InteliJ Idea*/
    public OrderDetail() {
    }

    public OrderDetail(String categoryName, int quantity, float productCost, float shippingCost, float subtotal) {
        this.product = new Product();
        this.product.setCategory(new Category(categoryName));
        this.quantity = quantity;
        this.productCost = productCost;
        this.shippingCost = shippingCost;
        this.subtotal = subtotal;
    }

    public OrderDetail(int quantity, String productName, float productCost, float shippingCost, float subtotal) {
        this.product = new Product(productName);
        this.quantity = quantity;
        this.productCost = productCost;
        this.shippingCost = shippingCost;
        this.subtotal = subtotal;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getProductCost() {
        return productCost;
    }

    public void setProductCost(float productCost) {
        this.productCost = productCost;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


}