package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.AdminDao;
import com.databps.bigdaf.admin.security.domain.Admin;
import com.databps.bigdaf.admin.service.AdminService;
import com.databps.bigdaf.admin.vo.AdminVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author merlin
 * @create 2017-08-31 下午2:17
 */
@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private AdminDao adminDao;

  @Override
  public List<AdminVo> findAdminPage(MongoPage pageable, String name,String id) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    List<Admin> adminList = adminDao.findAuditsPage(pageable, name,id);

    List<AdminVo> voList = new ArrayList<>();
    for (Admin admin : adminList) {
      AdminVo vm = new AdminVo();
      vm.setId(admin.getId());
      vm.setLogin(admin.getLogin());
      vm.setEmail(admin.getEmail());
      vm.setCreatedDate(admin.getCreatedDate());
      vm.setLastModifiedDate(admin.getLastModifiedDate());
      voList.add(vm);
    }

    return voList;
  }

  @Override
  public AdminVo findOne(String id) {
    Admin admin=adminDao.findOne(id);
    AdminVo vo=new AdminVo();
    vo.setId(admin.getId());
    vo.setEmail(admin.getEmail());
    vo.setLogin(admin.getLogin());
    vo.setPassword(admin.getPassword());
    return vo;
  }

  @Override
  public void update(AdminVo vo) {
    Admin admin=new Admin();
    admin.setEmail(vo.getEmail());
    admin.setLogin(vo.getLogin());
    admin.setId(vo.getId());
    adminDao.update(admin);
  }

  @Override
  public void delete(String id) {
    adminDao.delete(id);
  }
}
