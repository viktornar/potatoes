package com.sd.shop.potatoes.controllers.web;

import com.sd.shop.potatoes.services.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    SecurityService securityService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String doLogin(
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {
        String username = securityService.findLoggedInUsername();

        log.info("{}", username);

        model.addAttribute("error", false);

        if (error != null) {
            model.addAttribute("error", true);
        }

        return "login";
    }
}
