package com.dushop.admin.shippingrate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dushop.admin.product.ProductRepository;
import com.dushop.common.entity.ShippingRate;
import com.dushop.common.entity.product.Product;

/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.admin.shippingrate
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-08-29  23:35
 *@Description: TODO
 *@Version: 1.0
 */

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ShippingRateServiceTests {

    @MockBean private ShippingRateRepository shipRepo;
    @MockBean private ProductRepository productRepo;

    @InjectMocks
    private ShippingRateService shipService;

    @Test
    public void testCalculateShippingCost_NoRateFound() {
        Integer productId = 1;
        Integer countryId = 8;
        String state = "Dubai";

        Mockito.when(shipRepo.findByCountryAndState(countryId, state)).thenReturn(null);

        assertThrows(ShippingRateNotFoundException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                shipService.calculateShippingCost(productId, countryId, state);
            }
        });
    }

    @Test
    public void testCalculateShippingCost_RateFound() throws ShippingRateNotFoundException {
        Integer productId = 1;
        Integer countryId = 8;
        String state = "Dubai";

        ShippingRate shippingRate = new ShippingRate();
        shippingRate.setRate(10);

        Mockito.when(shipRepo.findByCountryAndState(countryId, state)).thenReturn(shippingRate);

        Product product = new Product();
        product.setWeight(5);
        product.setWidth(4);
        product.setHeight(3);
        product.setLength(8);

        Mockito.when(productRepo.findById(productId)).thenReturn(Optional.of(product));

        float shippingCost = shipService.calculateShippingCost(productId, countryId, state);

        assertEquals(50, shippingCost);
    }
}