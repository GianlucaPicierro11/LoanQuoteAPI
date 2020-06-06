package it.loanquote.log;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

public class RequestFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(RequestFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String role = request.getHeader("ROLE");
      String uid = request.getHeader("UID");
      // Setup MDC data:
      String mdcData = String.format("[uid= %s | role= %s]", uid, role);
      // Variable ‘mdcData’ is referenced in Spring Boot’s logging.pattern.level property
      MDC.put("mdcData", mdcData);
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      // Tear down MDC data:
      // ( Important! Cleans up the ThreadLocal data again )
      MDC.clear();
    }

  }
}
