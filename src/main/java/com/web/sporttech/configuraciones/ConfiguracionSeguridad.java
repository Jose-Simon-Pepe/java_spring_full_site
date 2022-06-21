package com.web.sporttech.configuraciones;

import com.web.sporttech.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter{
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/css/*","/js/*","/img/*","/**").permitAll()
                .and().formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/login-successfull").permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
    }
}
