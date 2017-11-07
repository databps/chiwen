package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.AuditDao;
import com.databps.bigdaf.admin.dao.BackUpDao;
import com.databps.bigdaf.admin.domain.BackUp;
import com.databps.bigdaf.admin.service.BackUpService;
import com.databps.bigdaf.admin.vo.BackUpVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author merlin
 * @create 2017-09-07 下午1:55
 */
@Service
public class BackUpServiceImpl implements BackUpService {
  @Autowired
  private BackUpDao backUpDao;

  @Override
  public List<BackUpVo> findBackUpPage(MongoPage pageable,String startDate,String endDate) {
    List<BackUp> backUpList= backUpDao.findAuditsPage(pageable,startDate,endDate);
    List<BackUpVo> backUpVoList=new ArrayList<>();
    for(BackUp backUp:backUpList){
      BackUpVo vo=new BackUpVo();
      vo.setCode(backUp.getCode());
      vo.setCatalog(backUp.getCatalog());
      vo.setCreateTime(backUp.getCreateTime());
      backUpVoList.add(vo);

    }
    return backUpVoList;

  }
}
