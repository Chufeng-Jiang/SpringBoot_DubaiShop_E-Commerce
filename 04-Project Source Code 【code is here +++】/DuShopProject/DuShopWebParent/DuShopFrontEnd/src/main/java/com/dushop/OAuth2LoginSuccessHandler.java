package com.dushop;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dushop.common.entity.AuthenticationType;
import com.dushop.common.entity.Customer;

/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.oauth
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-08-23  21:19
 *@Description: TODO
 *@Version: 1.0
 */

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired private CustomerService customerService;

    /************************************
     @Author:  Learn Programming with Real Apps
     “Completed E-Commerce with Spring MVC and Spring Data JPA Playlist” [online]
     Available at: https://youtu.be/hwSB50DqMkc
     ************************************/
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomerOAuth2User oauth2User = (CustomerOAuth2User) authentication.getPrincipal();

        String name = oauth2User.getName();
        String email = oauth2User.getEmail();
        String countryCode = request.getLocale().getCountry();
        String clientName = oauth2User.getClientName();

        System.out.println("OAuth2LoginSuccessHandler: " + name + " | " + email);
        System.out.println("Client name: " + clientName);

        AuthenticationType authenticationType = getAuthenticationType(clientName);

        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            customerService.addNewCustomerUponOAuthLogin(name, email, countryCode, authenticationType);
        } else {
            oauth2User.setFullName(customer.getFullName());
            customerService.updateAuthenticationType(customer, authenticationType);

        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
    /************************************
     @Author:  Learn Programming with Real Apps
     “Completed E-Commerce with Spring MVC and Spring Data JPA Playlist” [online]
     Available at: https://youtu.be/hwSB50DqMkc
     ************************************/
    private AuthenticationType getAuthenticationType(String clientName) {
        if (clientName.equals("Google")) {
            return AuthenticationType.GOOGLE;
        } else if (clientName.equals("Facebook")) {
            return AuthenticationType.FACEBOOK;
        } else {
            return AuthenticationType.DATABASE;
        }
    }

}