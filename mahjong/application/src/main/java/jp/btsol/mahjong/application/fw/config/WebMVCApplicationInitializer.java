package jp.btsol.mahjong.application.fw.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Component
public class WebMVCApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        Class<?>[] configurations = {WebConfiguration.class};
        return configurations;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        Class<?>[] configs = {};
        return configs;
    }

    @Override
    protected String[] getServletMappings() {
        String[] mappings = {"/"};
        return mappings;
    }

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
}