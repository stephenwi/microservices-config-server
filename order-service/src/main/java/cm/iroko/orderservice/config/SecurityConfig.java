package cm.iroko.orderservice.config;

@Security
@EnableWebSecurity
public class SecurityConfig {

    @PostConstruct
    public void enableAuthenticationContextOnSpawnedThreads() {
        SecurityContextHolder.setStrategyName(SecurityConfigHolder.MODE_INHERITABLETHREADLOCAL);
    }
}
