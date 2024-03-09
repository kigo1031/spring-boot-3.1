package my.project.shopping.service.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import my.project.shopping.domain.auth.PrincipalUserDetails;
import my.project.shopping.domain.auth.User;
import my.project.shopping.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        if (userId == null || userId.isBlank()) {
            throw new UsernameNotFoundException("Username cannot be null or empty");
        }

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );
        logger.info("UserService :: user : " + user.toString());

        return new PrincipalUserDetails(user);
    }
    public void setUidSession(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            User user = userRepository.findByUserId(authentication.getName())
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found")
                    );
            HttpSession session = request.getSession();
            session.setAttribute("uid", user.getUid());
        }
    }


}
