package com.databps.bigdaf.admin.service;


import com.databps.bigdaf.admin.security.domain.Admin;
import com.databps.bigdaf.admin.security.domain.Authority;
import java.util.Optional;
import java.util.Set;

/**
 * 用户
 *
 * @author merlin
 * @create 2017-07-20 下午7:44
 */
public interface UserService {


  Optional<Admin> findOneByLogin(String login);

  Optional<Admin> findOneByLogin(String login,String login2);

  Admin createUser(String login, String password, String firstName, String lastName, String email,
      String imageUrl, String langKey,String authoritieName,String parentId);

  Admin createUser(String login, String password, String firstName, String lastName, String email,
      String imageUrl, String langKey,Set<Authority> authorities,String parentId);


  boolean checkPwd(String login,String oldPwd);

  void updatePwd(String login,String newPwd);


}
