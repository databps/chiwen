package com.databps.bigdaf.admin.templateDirective;

import com.databps.bigdaf.admin.security.SecurityUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author merlin
 * @create 2017-08-17 下午7:53
 */
public class AuthorizeDirective implements TemplateDirectiveModel {

  private final String property = "role";

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
    if (map.containsKey(property) && map.get(property) != null) {
      String newProperty = map.get(property).toString();

      boolean isRole = SecurityUtils.isCurrentUserInRole(newProperty);
      Writer out = environment.getOut();
      if (isRole) {
        templateDirectiveBody.render(out);
      } else {
        out.write("");
      }


    }

  }
}
