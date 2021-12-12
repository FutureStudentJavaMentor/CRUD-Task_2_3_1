package web.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Метод, указывающий на класс конфигурации
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[0];


    }


    // Добавление конфигурации, в которой инициализируем ViewResolver, для корректного отображения jsp.
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class,DataSourceConfiguration.class
        };
    }


    /* Данный метод указывает url, на котором будет базироваться приложение */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }



    @Override
    public void onStartup(ServletContext ServletContext) throws ServletException {
        super.onStartup(ServletContext);
        FilterRegistration.Dynamic filterRegistration = ServletContext.addFilter("encoding-Filter",new CharacterEncodingFilter());
        filterRegistration.setInitParameter("encoding","UTF-8");
        filterRegistration.setInitParameter("forceEncoding","true");
        filterRegistration.addMappingForUrlPatterns(null,true,"/*");
        registerHiddenFieldFilter(ServletContext);

    }
    private void registerHiddenFieldFilter(ServletContext ServletContext) {
        ServletContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null,true, "/*");
    }


}
