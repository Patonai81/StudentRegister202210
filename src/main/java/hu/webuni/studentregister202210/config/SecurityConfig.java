package hu.webuni.studentregister202210.config;

import hu.webuni.studentregister202210.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;


    @Autowired
    JwtFilter jwtFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


 /* OLD'school
    @Bean
    public InMemoryUserDetailsManager getInMemoryUserDetailsManager(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager();

        inMemoryUserDetailsManager.createUser(User.withUsername("noel").password(passwordEncoder().encode("pass")).authorities("ADMIN").build());
        return inMemoryUserDetailsManager;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("user").authorities("USER").password(passwordEncoder().encode("pass"))
                .and()
                .withUser("admin").authorities("ADMIN").password(passwordEncoder().encode("pass"));
    }

*/


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  http.httpBasic()
        http.csrf().disable()
                //OAUTH miatt ki kell kapcsolni
                //   .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //   .and()
                .authorizeRequests()
            //    .antMatchers("https://www.facebook.com/**").permitAll()
            //    .antMatchers("/szabi/**").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/fbLoginSuccess").permitAll()
                .antMatchers("/topic/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/teacher/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/fbLoginSuccess", true);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
