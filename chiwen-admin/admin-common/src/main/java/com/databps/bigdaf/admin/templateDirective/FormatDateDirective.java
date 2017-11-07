package com.databps.bigdaf.admin.templateDirective;

import com.databps.bigdaf.core.util.DateUtils;
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
 * @create 2017-08-22 上午10:51
 */
public class FormatDateDirective implements TemplateDirectiveModel {


  private final String property = "value";

  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

    if (map.containsKey(property) && map.get(property) != null) {
      String newProperty = map.get(property).toString();
      String newDate = "0000-00-00 00:00:00";
      if (newProperty.equals("") || newProperty == null) {
        newDate = "0000-00-00 00:00:00";
      } else {
        newDate = DateUtils.format(DateUtils.parse(newProperty), DateUtils.YYYY_MM_DD_HH_MM_SS);
      }

      Writer out = environment.getOut();
      out.write(newDate);

    }

  }
}
