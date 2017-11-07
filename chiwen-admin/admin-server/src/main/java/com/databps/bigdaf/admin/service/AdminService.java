package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo.AdminVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

  List<AdminVo> findAdminPage(MongoPage pageable,String name,String id);


  AdminVo findOne(String id);

  void update(AdminVo vo);

  void delete(String id);

}
