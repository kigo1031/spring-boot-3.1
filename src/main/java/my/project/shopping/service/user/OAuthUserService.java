package my.project.shopping.service.user;

//@Slf4j
//@Service
//public class OAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
//    private final UserRepository userRepository;
//
//    public OAuthUserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        log.info("======================OAuthUserService.loadUser======================");
//        /**
//         * DefaultOAuth2UserService 객체를 생성하여, loadUser(userRequest)를 통해 DefaultOAuth2User 객체를 생성 후 반환
//         * DefaultOAuth2UserService의 loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서
//         * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환한다.
//         * 결과적으로, OAuth2User는 OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저
//         */
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        /**
//         * userRequest에서 registrationId 추출 후 registrationId으로 SocialType 저장
//         * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
//         * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
//         */
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        SocialType socialType = SocialType.valueOf(registrationId);
//        String userNameAttributeName = userRequest.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값
//        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)
//
//        // socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
//        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
//
////        User createdUser = getUser(extractAttributes, socialType); // getUser() 메소드로 User 객체 생성 후 반환
//
//        // DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
//        return oAuth2User;
//    }
//}

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.project.shopping.domain.auth.OAuthAttributes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("======================OAuthUserService.loadUser======================");
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        log.info("userNameAttributeName :: " + userNameAttributeName);
        log.info("attributes :: " + attributes.toString());

        httpSession.setAttribute("login_info", attributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());

    }
}
//
//attributes = {Collections$UnmodifiableMap@19269}  size = 3
//        "id" -> {Long@19277} 3357103158
//key = "id"
//value = {Long@19277} 3357103158
//value = 3357103158
//        "connected_at" -> "2024-02-22T10:27:28Z"
//key = "connected_at"
//value = "2024-02-22T10:27:28Z"
//        "kakao_account" -> {LinkedHashMap@19279}  size = 8
//key = "kakao_account"
//value = {LinkedHashMap@19279}  size = 8
//        "profile_nickname_needs_agreement" -> {Boolean@19170} false
//        "name_needs_agreement" -> {Boolean@19170} false
//        "name" -> "김겸"
//        "has_email" -> {Boolean@19175} true
//        "email_needs_agreement" -> {Boolean@19170} false
//        "is_email_valid" -> {Boolean@19175} true
//        "is_email_verified" -> {Boolean@19175} true
//        "email" -> "kigo307@kakao.com"

//id -> FS4RSPfKqlAL1oUqIQLv-cILhJxjUhRFgQLJvDk-yLs