package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 配置Spring Security
        http
            // 禁用CSRF保护
            .csrf(csrf -> csrf.disable())
            // 禁用Spring Security的表单登录功能，防止冲突
            .formLogin(formLogin -> formLogin.disable())
            // 禁用Spring Security的默认登录页
            .securityMatcher("/hello")
            // 配置URL权限
            .authorizeHttpRequests(authorize -> authorize
                // HelloWorld接口需要认证
                .anyRequest().authenticated()
            )
            // 使用HTTP Basic认证
            .httpBasic(withDefaults());

        return http.build();
    }

    // 为/login路径配置单独的安全过滤器链
    @Bean
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        http
            // 仅对/login路径应用此过滤器链
            .securityMatcher("/login")
            // 禁用CSRF保护
            .csrf(csrf -> csrf.disable())
            // 允许所有请求访问/login路径
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // 创建测试用户，用户名test，密码123456，角色USER
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("test")
            .password("123456")
            .roles("USER")
            .build();

        // 使用内存存储用户信息
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // 获取默认的认证管理器
        return authenticationConfiguration.getAuthenticationManager();
    }
}
