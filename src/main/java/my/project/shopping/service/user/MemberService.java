package my.project.shopping.service.user;

import my.project.shopping.domain.auth.SocialType;
import my.project.shopping.domain.auth.User;
import my.project.shopping.model.auth.UserRecord;
import my.project.shopping.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public MemberService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void userSignUp(UserRecord userRecord) {
        userRepository.save(User.builder()
                .userId(userRecord.userId())
                .name(userRecord.name())
                .password(passwordEncoder.encode(userRecord.password()))
                .email(userRecord.email())
                .phone(userRecord.phone())
                .role("ROLE_USER")
                .socialType(SocialType.HOMEPAGE)
                .build()
        );
    }

    public boolean isUserIdExist(String userId) {
        return userRepository.existsByUserId(userId);
    }
}

