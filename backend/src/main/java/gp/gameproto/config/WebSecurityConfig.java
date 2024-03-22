package gp.gameproto.config;

import gp.gameproto.service.UserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig { // 실제 인증 처리를 하는 시큐리티 설정파일
    private final UserDetailService userDetailService;

    @Bean // 1. 정적리소스의 경우 스프링 시큐리티 사용을 비활성화
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers("/static/**"); // static url ?? 필요 없을 듯?
    }


    @Bean // 2. 특정 HTTP 요청에 대해 웹 기반 보안 구성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth // 3. 인증 , 인가 설정
                        .requestMatchers("/login", "/signup").permitAll() // -> permitAll() 인증/인가 없이 접근 가능
                        .anyRequest().permitAll()//authenticated() //-> anyRequest : requestMatchers 이외 모든 요청, authenticated 인증 필요, 인가 불필요
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout( logout -> logout // 5. 로그아웃 설정
                        //.logoutSuccessUrl("/login") // 로그아웃 완료시 이동할 경로
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                        .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할지 여부 결정
                )
                .csrf(AbstractHttpConfigurer::disable) // CSRF 설정 비활성화 (편의)
                .build();
    }

    // 7. 인증 관리자(Authentication Manager) 관련 설정 (사용자 정보를 가져올 서비스 설정, 인증방법 설정)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){ // 9. pw 인코더로 사용할 빈 등록
        return new BCryptPasswordEncoder();
    }
}
