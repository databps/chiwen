package com.databps.bigdaf.admin.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author merlin
 * @create 2017-08-01 下午5:52
 */
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {
  private final Logger log = LoggerFactory
      .getLogger(io.github.jhipster.security.Http401UnauthorizedEntryPoint.class);

  public Http401UnauthorizedEntryPoint() {
  }

  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException {
    this.log.debug("Pre-authenticated entry point called. Rejecting access");
    //response.sendError(401, "Access Denied");
    response.sendRedirect("/authenticate");
  }
}
