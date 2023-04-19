package softuni.futsalleague.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.futsalleague.interseptor.LoggingInterceptor;
import softuni.futsalleague.interseptor.TeamInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;
    private final TeamInterceptor teamInterceptor;

    public InterceptorConfiguration(LoggingInterceptor loggingInterceptor, TeamInterceptor teamInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
        this.teamInterceptor = teamInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
        registry.addInterceptor(teamInterceptor).addPathPatterns("/api/teams/topThree");
    }
}
