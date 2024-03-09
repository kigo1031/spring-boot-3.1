package my.project.shopping.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import my.project.shopping.model.api.MyResponse;
import my.project.shopping.service.user.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class UserController {
    private final MemberService memberService;
    public UserController( MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(value = {"/login",}, method = {RequestMethod.GET})
    public String loginPage(HttpServletRequest request, Model model,
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "message", required = false) String message) {

        log.info("||||||||||||||||||||||login page||||||||||||||||||||||");
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        return "login";
    }

    @ResponseBody
    @PostMapping(value = {"/login/process",})
    public MyResponse loginProcessPage(HttpServletRequest request) {
        MyResponse myResponse = new MyResponse();
        boolean isIdExist = memberService.isUserIdExist(request.getParameter("userId"));
        if(isIdExist) {
            myResponse.setStatus(200);
        } else {
            myResponse.setStatus(400);
            myResponse.setMessage("아이디가 존재하지 않습니다.");
        }
        log.info("||||||||||||||||||||||login process||||||||||||||||||||||");
        return myResponse;
    }

    @ResponseBody
    @RequestMapping(value = {"/sessionCheck"})
    public ResponseEntity<String> sessionCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
        var uid = session.getAttribute("uid");
        var id = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        if(uid == null){
            return ResponseEntity.ok("없음 " + id);
        }else{

            return ResponseEntity.ok(uid.toString() + id);
        }
    }

    @RequestMapping(value = {"/logout/process"})
    public String logoutProcess(HttpServletRequest request) {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
