package shop.kokodo.promotionservice.circuitbreaker;


import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

@Component
public class AllCircuitBreaker {

    private static CircuitBreakerFactory circuitBreakerFactory;

    public AllCircuitBreaker(CircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public static CircuitBreaker createSellerCircuitBreaker() {
        return circuitBreakerFactory.create("promotionCircuitBreaker");
    }
}
