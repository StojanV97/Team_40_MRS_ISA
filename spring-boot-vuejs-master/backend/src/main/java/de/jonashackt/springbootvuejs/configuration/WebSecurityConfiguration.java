package de.jonashackt.springbootvuejs.configuration;

import de.jonashackt.springbootvuejs.security.TokenUtils;
import de.jonashackt.springbootvuejs.security.auth.RestAuthenticationEntryPoint;
import de.jonashackt.springbootvuejs.security.auth.TokenAuthenticationFilter;
import de.jonashackt.springbootvuejs.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;



    // Neautorizovani pristup zastcenim resursima
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    // @Autowired
    //private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Definisemo nacin autentifikacije
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Autowired
    TokenUtils tokenUtils;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable(); //ne radi konzola baze bez ove linije
        http
                // komunikacija izmedju klijenta i servera je stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // za neautorizovane zahteve posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                // svim korisnicima dopusti da pristupe putanjama /auth/**, /h2-console/** i /api/foo
                .authorizeRequests().antMatchers("/auth/**").permitAll().antMatchers("/h2/**").permitAll().antMatchers("/api/foo").permitAll()
                // svaki zahtev mora biti autorizovan
                .anyRequest().authenticated().and()
                .cors().and()
                // presretni svaki zahtev filterom
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
                BasicAuthenticationFilter.class);

        http.csrf().disable();

    }
    //Generalna bezbednost aplikacije
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
        web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/login");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js");
    }


}
