package com.dushop.shoppingcart;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dushop.Utility;
import com.dushop.common.entity.Customer;
import com.dushop.common.exception.CustomerNotFoundException;
import com.dushop.customer.CustomerService;
/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.shoppingcart
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-08-24  23:20
 *@Description: TODO
 *@Version: 1.0
 */

@RestController
public class ShoppingCartRestController {
    @Autowired private ShoppingCartService cartService;
    @Autowired private CustomerService customerService;

    @PostMapping("/cart/add/{productId}/{quantity}")
    public String addProductToCart(@PathVariable("productId") Integer productId,
                                   @PathVariable("quantity") Integer quantity, HttpServletRequest request) {

        try {
            Customer customer = getAuthenticatedCustomer(request);
            Integer updatedQuantity = cartService.addProduct(productId, quantity, customer);

            return updatedQuantity + " item(s) of this product were added to your shopping cart.";
        } catch (CustomerNotFoundException ex) {
            return "You must login to add this product to cart.";
        } catch (ShoppingCartException ex) {
            return ex.getMessage();
        }

    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request)
            throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No authenticated customer");
        }

        return customerService.getCustomerByEmail(email);
    }
}
