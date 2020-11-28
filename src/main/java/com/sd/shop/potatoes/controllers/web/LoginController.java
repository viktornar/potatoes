package com.sd.shop.potatoes.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String doLogin(
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {
        model.addAttribute("error", false);

        if (error != null) {
            model.addAttribute("error", true);
        }

        return "login";
    }
}
