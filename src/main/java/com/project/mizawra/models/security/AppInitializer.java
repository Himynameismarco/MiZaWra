package com.project.mizawra.models.security;

import jakarta.servlet.ServletContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

public class AppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext sc) {

    AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
    root.register(SecSecurityConfig.class);

    sc.addListener(new ContextLoaderListener(root));

    sc.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
        .addMappingForUrlPatterns(null, false, "/*");
  }
}
