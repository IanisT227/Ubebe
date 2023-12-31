package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by radu.
 */

@Configuration
@EnableWebMvc
@ComponentScan({"web"})
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200", "http://localhost:8080")
                        .allowedMethods("GET", "PUT", "POST", "DELETE");
            }
        };
    }

//    @Bean
//    ArticleRepository getArticleRepository() {
//        return new ArticleRepository(new ArticleValidator());
//    }

//    @Bean
//    CategoryRepository getCategoryRepository() {
//        return new CategoryRepository(new CategoryValidator());
//    }

//    @Bean
//    JournalistRepository getJournalistRepository() {
//        return new JournalistRepository(new JournalistValidator());
//    }

//    @Bean
//    ArticleJournalistRepository getArticleJournalistRepository() {
//        return new ArticleJournalistRepository(new ArticleJournalistValidator());
//    }
//
//    @Bean
//    ArticleJournalistServiceImpl articleJournalistService() {
//        return new ArticleJournalistServiceImpl(getArticleJournalistRepository());
//    }

//    @Bean
//    CategoryService categoryServiceImpl() {
//        return new CategoryServiceImpl(getCategoryRepository());
//    }

//    @Bean
//    JournalistServiceImpl journalistServiceImpl() {
//        return new JournalistServiceImpl(getJournalistRepository());
//    }

//    @Bean
//    ArticleService articleServiceImpl() {
//        return new ArticleServiceImpl(getArticleRepository());
//    }

//    @Bean
//    ExecutorService executorService() {
//        return Executors.newFixedThreadPool(
//                Runtime.getRuntime().availableProcessors()
//        );
//    }
}
