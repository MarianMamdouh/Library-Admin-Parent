package com.example.libraryadminapp.infrastructure.config;

import com.example.libraryadminapp.core.domain.auth.filter.AuthEntryPointJwt;
import com.example.libraryadminapp.core.domain.auth.filter.AuthTokenFilter;
import com.example.libraryadminapp.core.domain.auth.userdetails.service.impl.UserDetailsImpl;
import com.example.libraryadminapp.core.domain.auth.userdetails.service.impl.UserDetailsServiceImpl;
import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import com.example.libraryadminapp.core.domain.student.utils.Status;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.core.userdetails.User.builder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public UserDetailsService users() {
        Student student = Student.builder()
                .studentName("admin")
                .mobileNumber("")
                .password(passwordEncoder().encode("Admin@Aplus!"))
                .academicYear("")
                .facultyName("")
                .status(Status.ACTIVE)
                .build();

        if(!studentRepository.findByStudentName("admin").isPresent()) {

            studentRepository.save(student);
        }

        UserDetails user = UserDetailsImpl.build(student);
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/faculty").permitAll()
                .antMatchers("/academicYear").permitAll()
                .antMatchers("/students/register").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/students/verifyOTP").permitAll()
                .antMatchers("/students/setupFCMToken").permitAll()
                .antMatchers("/students/setupFCMToken").permitAll()
                .antMatchers("/students/delete").permitAll()
                .antMatchers("/", "/node_modules/**", "/style.css", "/runtime.js", "/polyfills.js", "/vendor.js",
                        "/main.js", "/favicon.ico", "/styles.css", "/primeicons.ttf", "/primeicons.woff").permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-ui/**").permitAll()
                .antMatchers("/students/login").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
