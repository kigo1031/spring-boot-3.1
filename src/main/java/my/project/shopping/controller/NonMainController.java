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
public class NonMainController {

    @RequestMapping(value = {"/services",}, method = {RequestMethod.GET})
    public String servicePage(HttpServletRequest request, Model model) {

        return "services";
    }
    @RequestMapping(value = {"/about",}, method = {RequestMethod.GET})
    public String aboutPage(HttpServletRequest request, Model model) {

        return "about";
    }
    @RequestMapping(value = {"/contact",}, method = {RequestMethod.GET})
    public String contactPage(HttpServletRequest request, Model model) {

        return "contact";
    }

}