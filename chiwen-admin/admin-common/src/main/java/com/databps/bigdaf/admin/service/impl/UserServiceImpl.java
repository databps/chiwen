package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.repository.AuthorityRepository;
import com.databps.bigdaf.admin.repository.UserRepository;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.security.SecurityUtils;
import com.databps.bigdaf.admin.security.domain.Admin;
import com.databps.bigdaf.admin.security.domain.Authority;
import com.databps.bigdaf.admin.service.UserService;
import com.databps.bigdaf.admin.service.util.RandomUtil;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author merlin
 * @create 2017-07-31 下午8:04
 */
@Service
public class UserServiceImpl implements UserService {

  private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthorityRepository authorityRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @Override
  public Optional<Admin> findOneByLogin(String login) {
    return userRepository.findOneByLogin(login);
  }

  @Override
  public Optional<Admin> findOneByLogin(String login, String login2) {
    return null;
  }

  @Override
  public Admin createUser(String login, String password, String firstName, String lastName,
      String email,
      String imageUrl, String langKey, String authoritieName,String parentId) {

    Admin newUser = new Admin();
    Authority authority = new Authority();
    authority.setName(authoritieName);
    Set<Authority> authorities = new HashSet<>();
    String encryptedPassword = passwordEncoder.encode(password);
    newUser.setLogin(login);
    // new user gets initially a generated password
    newUser.setPassword(encryptedPassword);
    newUser.setFirstName(firstName);
    newUser.setLastName(lastName);
    newUser.setEmail(email);
    newUser.setImageUrl(imageUrl);
    newUser.setLangKey(langKey);
    newUser.setParentId(parentId);
    // new user is not active
    newUser.setActivated(true);
    // new user gets registration key
    newUser.setActivationKey(RandomUtil.generateActivationKey());
    authorities.add(authority);
    newUser.setAuthorities(authorities);
    userRepository.save(newUser);
    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  @Override
  public Admin createUser(String login, String password, String firstName, String lastName,
      String email, String imageUrl, String langKey, Set<Authority> authorities,
      String parentId) {
    Admin newUser = new Admin();
    String encryptedPassword = passwordEncoder.encode(password);
    newUser.setLogin(login);
    // new user gets initially a generated password
    newUser.setPassword(encryptedPassword);
    newUser.setFirstName(firstName);
    newUser.setLastName(lastName);
    newUser.setEmail(email);
    newUser.setImageUrl(imageUrl);
    newUser.setLangKey(langKey);
    newUser.setParentId(parentId);
    // new user is not active
    newUser.setActivated(true);
    // new user gets registration key
    newUser.setActivationKey(RandomUtil.generateActivationKey());
    newUser.setAuthorities(authorities);
    userRepository.save(newUser);
    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  @Override
  public boolean checkPwd(String login, String oldPwd) {
    Optional<Admin> admin = userRepository.findOneByLogin(login);
    return passwordEncoder.matches(oldPwd, admin.get().getPassword());

  }

  @Override
  public void updatePwd(String login, String newPwd) {
    Optional<Admin> admin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
    Admin ad = admin.get();
    ad.setPassword(passwordEncoder.encode(newPwd));
    userRepository.save(ad);
  }
}
