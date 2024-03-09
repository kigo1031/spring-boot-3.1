package my.project.shopping.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import my.project.shopping.model.api.MyResponse;
import my.project.shopping.model.auth.UserRecord;
import my.project.shopping.service.user.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping(value = {"/join"})
public class JoinController {
    private final MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }


    @RequestMapping(value = {""})
    public String joinPage(HttpServletRequest request) {
        log.info("join page");
        return "join";
    }

    @ResponseBody
    @PostMapping(value ={"/process"})
    public MyResponse joinProcess(HttpServletRequest request, Model model, @ModelAttribute UserRecord userRecord) {
        boolean isUserIdExist = memberService.isUserIdExist(userRecord.userId());
        MyResponse response = new MyResponse();
        if(isUserIdExist){
            response.setStatus(400);
            response.setMessage("아이디가 존재합니다.");

            return response;
        }else{
            memberService.userSignUp(userRecord);
            response.setStatus(200);
            response.setMessage("회원가입이 완료되었습니다.");

            return response;
        }


    }
}
