package example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration //Dispatcher Servelet용 설정 클래스 작성
@EnableWebMvc //스프링 MVC가 제공하는 설정 클래스가 임포트 되어 스프링 MVC를 이용할때 필요한 컴포넌트의 빈 정의가 자동으로 이루어 진다.
@ComponentScan("example.app")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry){
        registry.jsp();
    }
    // configureViewResolvers를 registry.jsp()로 하면 /WEB-INF 아래 저장된 JSP 파일이 뷰로 취급된다.

}
