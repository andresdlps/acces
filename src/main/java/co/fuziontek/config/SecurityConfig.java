package co.fuziontek.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author FT
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
                                .csrf().disable()
				.authorizeRequests()
					.antMatchers("/css/**","/vendor/**","/img/**","/js/**", "/index").permitAll()
					.antMatchers("/acces/**").hasRole("USER")
					.and()
				.formLogin().loginPage("/login").failureUrl("/login-error");
	}
	// @formatter:on

	// @formatter:off
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.inMemoryAuthentication()
//				.withUser("user").password("password").roles("USER");
//	}
	// @formatter:on
}
