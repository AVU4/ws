package ifmo.ws.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint


@Configuration
@EnableWebSecurity
open class SecurityConfiguration {

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .authorizeHttpRequests { request ->
                    request
                            .requestMatchers(HttpMethod.POST, "/characters").anonymous()
                            .requestMatchers(HttpMethod.POST, "/character").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/character").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/character").authenticated()
                            .anyRequest().anonymous()
                }
                .httpBasic { customizer ->
                    customizer.authenticationEntryPoint(basicAuthenticationEntryPoint())
                }
                .csrf { csrf -> csrf.disable() }
                .userDetailsService(userDetailService())


        return http.build()
    }

    @Bean
    open fun basicAuthenticationEntryPoint(): BasicAuthenticationEntryPoint {
        val entryPoint = BasicAuthenticationEntryPoint()
        entryPoint.realmName = "Lab"
        return entryPoint
    }

    @Bean
    open fun userDetailService(): InMemoryUserDetailsManager {
        val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        val password = passwordEncoder.encode("password")
        println(password)
        val user: UserDetails = User.builder()
                .username("user")
                .password(password)
                .roles("USER")
                .build()

        return InMemoryUserDetailsManager(user)
    }
}