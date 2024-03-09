package my.project.shopping.config;

import my.project.shopping.service.user.OAuthUserService;
import my.project.shopping.service.user.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final OAuthUserService oAuthUserService;

    public SecurityConfig(UserService userService, OAuthUserService oAuthUserService) {
        this.userService = userService;
        this.oAuthUserService = oAuthUserService;
    }


//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) ->
//                web.ignoring()
//                        .requestMatchers(
//                                PathRequest.toStaticResources().atCommonLocations()
//                        )
//                // 추가사항
////                        .requestMatchers()
//                ;
//
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.headers(header -> {
//          header 설정
                    header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                }
        );
        //cors 설정
        http.cors(AbstractHttpConfigurer::disable);
        //csrf 설정
        http.csrf(AbstractHttpConfigurer::disable);

//        http.authenticationManager(authenticationManager(userService, passwordEncoder()));

        http.sessionManagement(sessionManagement -> sessionManagement
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/login")
                .sessionRegistry(sessionRegistry())
        );

        http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/mypage").authenticated() // 로그인 상태에서만 접금 가능한 페이지 추가
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .anyRequest().permitAll()
                )
                .formLogin(login -> login.loginPage("/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler(successHandler())
                        .failureHandler(failureHandler())
                        .permitAll()
                )
                .logout(logout -> logout.
                                logoutUrl("/logout")
                                .logoutSuccessUrl("/logout/process")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                                // 로그아웃 시 세션 쿠키를 포함한 모든 항목을 정리
////                        .addLogoutHandler(new HeaderWriterLogoutHandler(
////                                        new ClearSiteDataHeaderWriter(
////                                                ClearSiteDataHeaderWriter.Directive.CACHE,
////                                                ClearSiteDataHeaderWriter.Directive.COOKIES,
////                                                ClearSiteDataHeaderWriter.Directive.STORAGE
////                                        )
////                                )
////                        )
                                .permitAll()
                );
        http.oauth2Login(
                oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuthUserService)
                        )
        );
        http.oauth2Client(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}