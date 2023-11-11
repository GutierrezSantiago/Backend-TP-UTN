package ar.edu.frc.utn.bda.apigwalquilerDeBicicletasApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class GWConfig {
    private final String[] swaggerUIPaths = {"/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**", "/favicon.ico", "/swagger-ui/index.html"};
    // ROUTING
    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${api-gw.url-microservicio-alquileres}") String uriAlquileres,
                                        @Value("${api-gw.url-microservicio-estaciones}") String uriEstaciones) {
        return builder.routes()
                // Ruteo al MS Alquileres
                .route(p -> p.path("/api/alquiler/**").uri(uriAlquileres))
                // Ruteo al MS Estaciones
                .route(p -> p.path("/api/estacion/**").uri(uriEstaciones))
                .build();

    }

    // SECURITY
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges

                        // ESTACION
                        .pathMatchers(HttpMethod.GET,"/api/estacion/**")
                        .hasAnyRole("CLIENTE", "ADMINISTRADORE")

                        .pathMatchers(HttpMethod.POST,"/api/estacion/**")
                        .hasRole("ADMINISTRADOR")


                        // ALQUILERES
                        .pathMatchers(HttpMethod.POST,"/api/alquiler/**")
                        .hasRole("CLIENTE")

                        .pathMatchers(HttpMethod.PUT,"/api/alquiler/**")
                        .hasRole("CLIENTE")

                        .pathMatchers(HttpMethod.GET,"/api/alquiler/**")
                        .hasRole("ADMINISTRADOR")

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
