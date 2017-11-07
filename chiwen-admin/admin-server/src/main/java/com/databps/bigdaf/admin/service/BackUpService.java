package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo.BackUpVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author merlin
 * @create 2017-09-07 下午1:55
 */
public interface BackUpService {

  List<BackUpVo> findBackUpPage(MongoPage pageable,String startDate,String endDate);

}
