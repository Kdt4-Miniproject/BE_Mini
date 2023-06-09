package org.vacation.back.config;


import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.vacation.back.filter.AccessCheckFilter;
import org.vacation.back.filter.RefreshCheckFilter;
import org.vacation.back.filter.TokenAuthenticationFilter;
import org.vacation.back.security.JwtAuthenticationProvider;
import org.vacation.back.security.handler.TokenFailureHandler;
import org.vacation.back.security.handler.TokenSuccessHandler;
import org.vacation.back.security.service.MemberDetailsService;
import org.vacation.back.utils.JWTUtils;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTUtils jwtUtils;

    private final MemberDetailsService memberDetailsService;



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.cors().configurationSource(configurationSource());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(new JwtAuthenticationProvider(memberDetailsService,passwordEncoder()));

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.authenticationManager(authenticationManager);
        http.addFilterBefore(new AccessCheckFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests(
                authorize -> authorize.anyRequest().permitAll()

        );
        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter("/api/v1/login",authenticationManager);
        tokenAuthenticationFilter.setAuthenticationSuccessHandler(new TokenSuccessHandler(jwtUtils));
        tokenAuthenticationFilter.setAuthenticationFailureHandler(new TokenFailureHandler());


        http.addFilterAt(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new RefreshCheckFilter("/api/v1/refresh",jwtUtils), TokenAuthenticationFilter.class);
        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
