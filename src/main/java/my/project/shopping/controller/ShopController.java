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
public class ShopController {


    @RequestMapping(value = {"/shop",}, method = {RequestMethod.GET})
    public String shopPage(HttpServletRequest request, Model model) {

        return "product";
    }

    @RequestMapping(value = {"/single",}, method = {RequestMethod.GET})
    public String singleStuffPage(HttpServletRequest request, Model model) {

        return "single";
    }
}