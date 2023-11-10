package ar.edu.frc.utn.bda.apigwalquilerDeBicicletasApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GWConfig {
    // ROUTING
    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${apunte-api-gw-kempes.url-microservicio-personas}") String uriPersonas,
                                        @Value("${apunte-api-gw-kempes.url-microservicio-entradas}") String uriEntradas) {
        return builder.routes()
                // Ruteo al Microservicio de Personas
                .route(p -> p.path("/api/personas/**").uri(uriPersonas))
                // Ruteo al Microservicio de Entradas
                .route(p -> p.path("/api/entradas/**").uri(uriEntradas))
                .build();

    }

    // SECURITY
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges

                        // Esta ruta puede ser accedida por cualquiera, sin autorización
                        .pathMatchers("/api/personas/**")
                        .hasRole("KEMPES_ADMIN")

                        .pathMatchers("/api/entradas/**")
                        .hasRole("KEMPES_ORGANIZADOR")

                        // Cualquier otra petición...
                        .anyExchange()
                        .authenticated()

                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // AUTH CONVERTER
    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Se especifica el nombre del claim a analizar
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        // Se agrega este prefijo en la conversión por una convención de Spring
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        // Se asocia el conversor de Authorities al Bean que convierte el token JWT a un objeto Authorization
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));
        // También se puede cambiar el claim que corresponde al nombre que luego se utilizará en el objeto
        // Authorization
        // jwtAuthenticationConverter.setPrincipalClaimName("user_name");

        return jwtAuthenticationConverter;
    }
}
