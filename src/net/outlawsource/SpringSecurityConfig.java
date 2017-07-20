package net.outlawsource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import net.outlawsource.data.DatabaseManager;

/*@EnableWebMvc
@Configuration*/
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DatabaseManager databaseManager;

	/*@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(databaseManager.getDataSource())
				.usersByUsernameQuery("select user_id,password from user where user_id=?")
				.authoritiesByUsernameQuery("select user_id, role from user_role where user_id=?");
	}*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/stats").access("hasRole('webuser')").anyRequest().permitAll().and()
				.formLogin().loginPage("/login").usernameParameter("user_id").passwordParameter("password").and()
				.logout().logoutSuccessUrl("/logout").and().exceptionHandling().accessDeniedPage("/403").and().csrf();
	}
}
