package com.databps.bigdaf.admin.templateDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * @author merlin
 * @create 2017-08-22 上午10:51
 */
public class LoopholeDirective implements TemplateDirectiveModel {

  private String url = null;

  public LoopholeDirective(String url) {
    if(StringUtils.isNoneBlank(url)) {
      url = url+"/scan/index";
    }
    this.url = url;
  }

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

    Writer out = environment.getOut();
    out.write(url);

  }
}
