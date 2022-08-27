package com.dushop.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dushop.common.entity.Address;
import com.dushop.common.entity.Customer;
import com.dushop.common.entity.ShippingRate;

/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.shipping
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-08-26  23:32
 *@Description: TODO
 *@Version: 1.0
 */

@Service
public class ShippingRateService {

    @Autowired private ShippingRateRepository repo;

    public ShippingRate getShippingRateForCustomer(Customer customer) {
        String state = customer.getState();
        if (state == null || state.isEmpty()) {
            state = customer.getCity();
        }

        return repo.findByCountryAndState(customer.getCountry(), state);
    }

    public ShippingRate getShippingRateForAddress(Address address) {
        String state = address.getState();
        if (state == null || state.isEmpty()) {
            state = address.getCity();
        }

        return repo.findByCountryAndState(address.getCountry(), state);
    }
}
