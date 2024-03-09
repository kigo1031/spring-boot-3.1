package my.project.shopping.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * HOMECONTROLLER
 */
 @Controller()
public class HomeController {

    @RequestMapping(value = {"", "/", "index"}, method={RequestMethod.GET, RequestMethod.POST})
    public String requestMethodName(HttpServletRequest request, Model model) {
        return "index";
    }

}