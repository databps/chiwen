package com.databps.bigdaf.admin.config;

import com.databps.bigdaf.admin.BigDafApplication;
import com.databps.bigdaf.admin.freemarker.view.resolver.RichFreeMarkerViewResolver;
import com.databps.bigdaf.admin.templateDirective.AuthenticationDirective;
import com.databps.bigdaf.admin.templateDirective.AuthorizeDirective;
import com.databps.bigdaf.admin.templateDirective.EndDateDirective;
import com.databps.bigdaf.admin.templateDirective.FormatDateDirective;
import com.databps.bigdaf.admin.templateDirective.LoopholeDirective;
import com.databps.bigdaf.admin.templateDirective.StartDateDirective;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class BigDafConfig extends WebMvcConfigurerAdapter {

    @Value("${server.bigdaf.loophole.hostname}")
    private String LOOPHOLE_HOSTNAME;


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());

    }

    public String getLoopholeHostname() {
        return LOOPHOLE_HOSTNAME;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter jsonConverter = new StringHttpMessageConverter();
        jsonConverter.setWriteAcceptCharset(false);
        List<MediaType> mediaTypeList = new ArrayList<MediaType>();
        mediaTypeList.add(new MediaType("application", "json", Charset.forName("UTF-8")));
        jsonConverter.setSupportedMediaTypes(mediaTypeList);
        return jsonConverter;
    }


    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPath("classpath:/templates/");
        factory.setDefaultEncoding("UTF-8");
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        Properties settings = new Properties();
        settings.put("classic_compatible", "true");
        settings.put("output_format", "HTMLOutputFormat");
        settings.put("tag_syntax", "square_bracket");
        settings.put("whitespace_stripping", "true");
        settings.put("number_format", "#");
        settings.put("cache_storage", "freemarker.cache.NullCacheStorage");
        settings.put("template_update_delay", "0");
        settings.put("locale", "en");
        settings.put("auto_import", "ftl/index.ftl as p");
        factory.setFreemarkerSettings(settings);
        freemarker.template.Configuration conf = factory.createConfiguration();
        conf.setSharedVariable("authentication", new AuthenticationDirective());
        conf.setSharedVariable("authorize", new AuthorizeDirective());
        conf.setSharedVariable("loophole", new LoopholeDirective(getLoopholeHostname()));
        conf.setSharedVariable("formatDate", new FormatDateDirective());
        conf.setSharedVariable("startDate", new StartDateDirective());
        conf.setSharedVariable("endDate", new EndDateDirective());
        result.setConfiguration(conf);
        return result;
    }


    @Bean
    public ViewResolver freeMarkerViewResolver() {
        RichFreeMarkerViewResolver viewResolver = new RichFreeMarkerViewResolver();
        viewResolver.setCache(false);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".html");
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setExposeSpringMacroHelpers(true);
        viewResolver.setExposeRequestAttributes(false);
        viewResolver.setExposeSessionAttributes(false);

        return viewResolver;
    }


    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }


    @Bean
    public File calcLauncherDir() throws IOException {
        File launcherJarDir = calcLauncherDir(findLauncherJar());
        return launcherJarDir;
    }

    private File calcLauncherDir(URL libUrl) throws IOException {
        String libPath = URLDecoder.decode(libUrl.getPath(), "UTF-8");
        int indexof = libPath.indexOf("file:");
        int endof = libPath.indexOf("!/");
        if (indexof != -1 && endof !=-1) {
            libPath = libPath.substring(5,endof);
        }
        if (indexof != -1 && endof ==-1) {
            libPath = libPath.substring(5);
        }

        File libFile = new File(libPath);
        File dir;
        if (libFile.isDirectory()) {
            dir = libFile;
        } else {
            dir = libFile.getParentFile();
        }
        // System.out.println("main.dir=" + dir.getAbsolutePath());
        return dir;
    }

    private URL findLauncherJar() {
        URL jarUrl = BigDafApplication.class.getProtectionDomain().getCodeSource().getLocation();
        return jarUrl;
    }

//  @Bean
//  public CorsFilter corsFilter() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowedOrigins(Arrays.asList("*"));
//    configuration.setAllowedMethods(Arrays.asList("*"));
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//
//    return new CorsFilter(source);
//  }
}
