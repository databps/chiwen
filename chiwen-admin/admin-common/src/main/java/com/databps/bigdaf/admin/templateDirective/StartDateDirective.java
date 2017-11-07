package com.databps.bigdaf.admin.templateDirective;

import com.databps.bigdaf.core.util.DateUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

/**
 * @author merlin
 * @create 2017-08-22 上午10:51
 */
public class StartDateDirective implements TemplateDirectiveModel {


  @Override
  public void execute(Environment environment, Map map, TemplateModel[] templateModels,
      TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

    String startDate = DateUtils.formatLastDayDate(-2,DateUtils.YYYY_MM_DD_HH_MM);
    Writer out = environment.getOut();
    out.write(startDate);

  }
}
