package com.sd.shop.potatoes.services;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
//    User findLoggedUser();

    String findLoggedInUsername();

    String getCurrentUsername(final HttpServletRequest request);

    void autologin(String username, String password);
}
