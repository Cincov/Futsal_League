package softuni.futsalleague.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.futsalleague.interseptor.LoggingInterceptor;
import softuni.futsalleague.interseptor.TableInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;
    private final TableInterceptor tableInterceptor;

    public InterceptorConfiguration(LoggingInterceptor loggingInterceptor, TableInterceptor tableInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
        this.tableInterceptor = tableInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
        registry.addInterceptor(tableInterceptor);
    }
}
