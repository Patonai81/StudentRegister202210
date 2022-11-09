package hu.webuni.studentregister202210.config;

import hu.webuni.studentregister202210.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }



    @Bean
    public InMemoryUserDetailsManager getInMemoryUserDetailsManager(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager();

        inMemoryUserDetailsManager.createUser(User.withUsername("noel").password(passwordEncoder().encode("pass")).authorities("ADMIN").build());
        return inMemoryUserDetailsManager;
    }

    /* OLD'school
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("user").authorities("USER").password(passwordEncoder().encode("pass"))
                .and()
                .withUser("admin").authorities("ADMIN").password(passwordEncoder().encode("pass"));
    }

*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(getInMemoryUserDetailsManager()).passwordEncoder(passwordEncoder());

    }


        @Override
    protected void configure(HttpSecurity http) throws Exception {
      //  http.httpBasic()
                http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/login/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/teacher/**").hasAnyAuthority("USER","ADMIN")
                .anyRequest()
                .authenticated();

                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
