
package com.databps.bigdaf.admin.freemarker.view.resolver;

import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.ext.servlet.IncludePage;
import freemarker.template.SimpleHash;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * 扩展spring的FreemarkerView，加上base属性。
 *
 * 支持jsp标签，Application、Session、Request、RequestParameters属性
 */
public class RichFreeMarkerView extends FreeMarkerView {

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  protected void doRender(Map model, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    exposeModelAsRequestAttributes(model, request);
    SimpleHash fmModel = buildTemplateModel(model, request, response);
    fmModel.put(FreemarkerServlet.KEY_INCLUDE, new IncludePage(request, response));
    fmModel.put("param", new HttpRequestParametersHashModel(request));
    fmModel.put("base", request.getContextPath());

    fmModel.put("t", request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
    Locale locale = RequestContextUtils.getLocale(request);
    processTemplate(getTemplate(locale), fmModel, response);
  }

}