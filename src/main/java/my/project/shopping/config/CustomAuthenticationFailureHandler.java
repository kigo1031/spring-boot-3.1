package my.project.shopping.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = getExceptionMessage(exception);
        response.sendRedirect("/login?error=ture&message="+message);
    }




    private String getExceptionMessage(AuthenticationException exception) throws UnsupportedEncodingException {
        String message;
        if(exception instanceof BadCredentialsException){
            message = "비밀번호가 일치하지 않습니다.";
        }else if(exception instanceof UsernameNotFoundException) {
            message = "아이디가 존재하지 않습니다.";
        }else{
            message = "로그인에 실패하였습니다.";
        }
        return URLEncoder.encode(message, "UTF-8");
    }
}
