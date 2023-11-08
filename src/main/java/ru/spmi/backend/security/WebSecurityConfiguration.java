package ru.spmi.backend.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.spmi.backend.services.RoleDAO;
import ru.spmi.backend.data.Roles;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;
//    @Autowired
//    private RoleDAO roleDAO;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    private String roles(){
//        String str ;// = new String[]{"COUNCIL", "COUNCIL_SUPER"};
//        str=roleDAO.getAllRoles();
//        System.out.println(str);
//        return  str;
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        String[] str  = Roles.getAllRoles();//new String[]{"COUNCIL", "COUNCIL_SUPER"};
        httpSecurity.authorizeHttpRequests((authz) -> authz
                                .requestMatchers("/api/auth/**", "/home/**").permitAll()
//                                .requestMatchers("/swagger-ui/index.html#/").permitAll()
                                //.requestMatchers("/api/university/**").hasAnyAuthority("COUNCIL", "COUNCIL_SUPER")
                                .requestMatchers("/api/university/**").hasAnyAuthority(str )
                             //   .requestMatchers("/api/science/**").hasAnyAuthority("COUNCIL", "COUNCIL_SUPER")
                               // .requestMatchers("/api/student/**").hasAnyAuthority("COUNCIL", "COUNCIL_SUPER")

//                       .requestMatchers("/api/teacher/**").hasAnyAuthority( "TEACHER", "ADMIN","DORMITORY")
//                        .requestMatchers("/api/student/**").hasAnyAuthority("STUDENT", "ADMIN","DORMITORY")
                                .anyRequest().authenticated()
                )
                // устанавливает стандартных шаблон http
                .httpBasic(Customizer.withDefaults())
                .formLogin()
                .loginPage("/login")
                .and()
                .cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       // System.out.println(httpSecurity.);
        // ставит наш кастомный jwt фильтр (работает перед каждым запросом)
        httpSecurity.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200","http://nit-frontend1-virt1.spmi.ru");
//                registry.addMapping("/**").allowedOrigins("http://nit-frontend1-virt1.spmi.ru:22");

            }
        };
    }

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

}


