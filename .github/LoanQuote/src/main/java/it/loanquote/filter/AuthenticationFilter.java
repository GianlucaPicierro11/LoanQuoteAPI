package it.loanquote.filter;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import it.loanquote.dtos.UserDTO;

public class AuthenticationFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

  private Boolean testMode;

  public AuthenticationFilter(Boolean testMode) {
    super();
    log.info("Initializing the AuthenticationFilter...");
    this.testMode = testMode;
    log.info("AuthenticationFilter initialized");
  }

  public Boolean getTestMode() {
    return testMode;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    log.info("Retrieving header variables from the request...");
    String role = request.getHeader("ROLE");
    String uid = request.getHeader("UID");

    UserDTO user = null;
    
    if (role != null && !role.isEmpty() && uid != null && !uid.isEmpty()) {
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
      user =  new UserDTO("admin", "admin", Arrays.asList(grantedAuthority));
      user.setRole(role);
      user.setUid(uid);
    } else {
      log.warn(
          "No request header variable retrieved. Test mode unactive. No User will be created");
    }

    if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      log.info("Setting the authentication principal...");
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContext sc = SecurityContextHolder.getContext();
      sc.setAuthentication(authentication);
    }

    chain.doFilter(request, response);
  }
}
