package com.dushop.admin.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.dushop.admin.setting.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.dushop.common.entity.Currency;

/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.admin.setting
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-08-13  19:26
 *@Description: TODO
 *@Version: 1.0
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CurrencyRepositoryTests {

    @Autowired
    private CurrencyRepository repo;

    @Test
    public void testCreateCurrencies() {
        List<Currency> listCurrencies = Arrays.asList(
                new Currency("United States Dollar", "$", "USD"),
                new Currency("United Arab Emirates", "د.إ", "UAE"),
                new Currency("British Pound", "£", "GPB"),
                new Currency("Japanese Yen", "¥", "JPY"),
                new Currency("Euro", "€", "EUR"),
                new Currency("Russian Ruble", "₽", "RUB"),
                new Currency("South Korean Won", "₩", "KRW"),
                new Currency("Chinese Yuan", "¥", "CNY"),
                new Currency("Brazilian Real", "R$", "BRL"),
                new Currency("Australian Dollar", "$", "AUD"),
                new Currency("Canadian Dollar", "$", "CAD"),
                new Currency("Vietnamese đồng ", "₫", "VND"),
                new Currency("Indian Rupee", "₹", "INR")
        );

        repo.saveAll(listCurrencies);

        Iterable<Currency> iterable = repo.findAll();

        assertThat(iterable).size().isEqualTo(12);
    }

    @Test
    public void testListAllOrderByNameAsc() {
        List<Currency> currencies = repo.findAllByOrderByNameAsc();

        currencies.forEach(System.out::println);

        assertThat(currencies.size()).isGreaterThan(0);
    }
}
