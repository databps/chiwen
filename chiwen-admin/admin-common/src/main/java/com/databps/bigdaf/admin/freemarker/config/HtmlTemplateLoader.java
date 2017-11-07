package com.databps.bigdaf.admin.freemarker.config;

import com.google.common.io.CharStreams;
import freemarker.cache.TemplateLoader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author merlin
 * @create 2017-09-06 下午3:57
 */
public class HtmlTemplateLoader implements TemplateLoader {

  public static final String ESCAPE_PREFIX = "<#escape x as x?html>";
  public static final String ESCAPE_SUFFIX = "</#escape>";

  private final TemplateLoader delegate;

  public HtmlTemplateLoader(TemplateLoader delegate) {
    this.delegate = delegate;
  }

  @Override
  public Object findTemplateSource(String name) throws IOException {
    return delegate.findTemplateSource(name);
  }

  @Override
  public long getLastModified(Object templateSource) {
    return delegate.getLastModified(templateSource);
  }

  @Override
  public Reader getReader(Object templateSource, String encoding) throws IOException {
    if (isLibraryTemplate(templateSource)) {
      return delegate.getReader(templateSource, encoding);
    } else {
      try (Reader reader = delegate.getReader(templateSource, encoding)) {
        String templateText = CharStreams.toString(reader);
        return new StringReader(ESCAPE_PREFIX + templateText + ESCAPE_SUFFIX);
      }
    }
  }

  private boolean isLibraryTemplate(Object templateSource) {
    return templateSource.toString().startsWith("jar:");
  }

  @Override
  public void closeTemplateSource(Object templateSource) throws IOException {
    delegate.closeTemplateSource(templateSource);
  }
}