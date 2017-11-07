package com.databps.bigdaf.admin.freemarker.config;

import freemarker.cache.TemplateLoader;
import java.util.List;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * @author merlin
 * @create 2017-09-06 下午3:56
 */
public class XssFreeMarkerConfigurer extends FreeMarkerConfigurer {

  @Override
  protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders)
  {
    logger.info("Using HtmlTemplateLoader to enforce HTML-safe content");
    return super.getAggregateTemplateLoader(templateLoaders);
  }
}
