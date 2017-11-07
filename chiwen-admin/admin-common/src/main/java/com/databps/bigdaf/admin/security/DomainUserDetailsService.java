package com.databps.bigdaf.admin.security;

import com.databps.bigdaf.admin.repository.UserRepository;
import com.databps.bigdaf.admin.security.domain.Admin;
import com.databps.bigdaf.admin.security.domain.SSOAdmin;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

  private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

  private final UserRepository userRepository;

  public DomainUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(final String login) {
    log.debug("Authenticating {}", login);
    String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
    Optional<Admin> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
    return userFromDatabase.map(admin -> {
      if (!admin.getActivated()) {
        throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
      }
      List<GrantedAuthority> grantedAuthorities = admin.getAuthorities().stream()
          .map(authority -> new SimpleGrantedAuthority(authority.getName()))
          .collect(Collectors.toList());
      return new SSOAdmin(lowercaseLogin,"11111111111",admin.getId(),
          admin.getPassword(),
          grantedAuthorities);
    }).orElseThrow(
        () -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
            "database"));
  }
}
