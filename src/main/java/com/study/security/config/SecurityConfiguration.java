package com.study.security.config;

import com.study.security.enums.MemberRole;
import com.study.security.enums.UserAuthority;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    /**
     * Spring Security 에서 사용자가 로그인하면, SecurityContext 라는 개념을 사용하여 현재 사용자의 보안 정보를 추적한다
     * SecurityContext 에는 Authentication 객체가 포함되어 있다
     * 사용자의 로그인상태, 사용자 이름 및 권한과 같은 정보는 이 Authentication 객체를 통해 사용할 수 있다
     * isAuthenticated()
     *  - isAuthenticated()는 현재 사용자가 인증되었는지 여부를 나타냄
     *  - 'true' 인 경우 사용자는 성공적으로 인증되었음을 의미하며, 'false' 인 경우 익명사용자 또는 인증되지 않은 사용자를 나타냄
     *  - 이 값은 Authentication 객체의 isAuthenticated() 메서드를 통해 확인됨
     *
     * remoteUser
     *  - 'remoteUser' 는 현재 사용자의 이름을 나타냄
     *  - Spring Security 에서는 사용자 이름을 주로 "principal" 이라고도 부른다
     *  - Authentication 객체의 getName() 메서드를 통해 현재 사용자의 이름을 얻을 수 있다
     *
     * principal.authorities
     *  - principal.authorities 는 현재 사용자의 권한 목록을 나타냄
     *  - Spring Security 에서 권한은 GrantedAuthority 인터페이스를 구현하는 객체로 표현됨
     *  - Authentication 객체의 getAuthorities() 메서드를 통해 현재 사용자의 권한 목록을 얻을 수 있다
     *  - 권한은 주로 역할(Role) 또는 특정 동작에 대한 권한(Authority) 일 수 있다
     */

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/home", "/signUp").permitAll() // "/" 및 "/home" 경로에 대한 모든 사용자 허용
                    .antMatchers("/system").hasRole(MemberRole.SYSTEM.toString()) // SYSTEM 역할을 가지고 있어야 접근 허용
                    .antMatchers("/system/create").access("hasRole('" + MemberRole.SYSTEM + "') and hasAuthority('" + UserAuthority.OP_CREATE_DATA + "')") // SYSTEM 역할과 OP_CREATE_DATA 권한을 가지고 있어야 접근 허용
                    .antMatchers("/system/delete").access("hasRole('" + MemberRole.SYSTEM + "') and hasAuthority('" + UserAuthority.OP_DELETE_DATA + "')") // SYSTEM 역할과 OP_DELETE_DATA 권한을 가지고 있어야 접근 허용
                    .anyRequest().authenticated() // 그 외의 모든 요청에 대해 인증된 사용자만 허용
                    .and()
                .formLogin()
                    .permitAll()
                    .loginPage("/login") // 로그인 페이지를 "/login"으로 설정
                    .and()
                .logout()
                    .permitAll()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 주소창에 요청해도 포스트로 인식하여 로그아웃
                    .deleteCookies("JSESSIONID") // 로그아웃 시 JSESSIONID 제거
                    .invalidateHttpSession(true) // 로그아웃 시 세션 종료
                    .clearAuthentication(true) // 로그아웃 시 권한 제거
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/accessDenied");

        return http.build(); // 구성된 보안 설정 반환
    }

    /**
     * AuthenticationManagerBuilder 에 패스워드 암호화를 위해 Spring Security 에서 제공하는
     * BCryptPasswordEncoder 를 추가 후 UserDetailsService 를 추가하여 로그인 시 UserDetailsService 를 구현한
     * CustomUserDetailsService 에서 사용자 확인 및 권한을 넣어줄 수 있도록 한다
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
