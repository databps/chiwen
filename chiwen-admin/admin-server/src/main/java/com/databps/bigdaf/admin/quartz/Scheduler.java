package com.databps.bigdaf.admin.quartz;

import com.databps.bigdaf.admin.domain.Audit;
import com.databps.bigdaf.admin.domain.AuditStatistics;
import com.databps.bigdaf.admin.manager.HomePageManager;
import com.databps.bigdaf.admin.security.domain.AbstractAuditingEntity;
import com.databps.bigdaf.admin.service.AuditService;
import com.databps.bigdaf.admin.service.HbaseService;
import com.databps.bigdaf.admin.vo.AuditDailyStatisticsVo;
import com.databps.bigdaf.admin.vo.HomePageVo;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.util.DateUtils;
import com.databps.bigdaf.core.util.OpConstant;
import org.apache.hadoop.hdfs.security.token.block.DataEncryptionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haipeng
 * @create 17-10-10 下午4:02
 */
@Component
public class Scheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuditService auditService;

    @Autowired
    private HomePageManager homePageManager;

    @Scheduled(fixedRate=180000) //每3分钟执行一次
    public void AuditDailyStatisticsTask() {
       try {
           //获取今天的审计数据
           List<AuditStatistics> list=auditService.getDailyAuditStatistics("5968802a01cbaa46738eee3d");
           //批量插入数据
           Map<String,AuditDailyStatisticsVo> map=auditStatisticToVo(list);
           auditService.insertDailyAuditStatistics(map);
       }catch (Exception ex){
//           ex.printStackTrace();
       }
    }

    @Scheduled(cron="0 0/2 * * * ? ")
    public void AuditHomePage(){
        String	cmpyId = "5968802a01cbaa46738eee3d";
        HomePageVo vo = homePageManager.getHomePageVo(cmpyId);
        if(vo!=null){
            auditService.insertAuditHomePage(vo);
        }
    }


    /**
     * 每天0点插入审计空值，便于首页统计
     */
    @Scheduled(cron="0 0 0 * * ? ")
    public void AuditDailyInsertNull() {
        try {
            auditService.inserDailyNull();
        }catch (Exception ex){
//           ex.printStackTrace();
        }
    }

//    @Scheduled(fixedRate=20000)
    public void testTasks() {
        logger.info("每20秒执行一次。开始……");
        //statusTask.healthCheck();
        logger.info("每20秒执行一次。结束。");
    }

    private Map<String,AuditDailyStatisticsVo> auditStatisticToVo(List<AuditStatistics> list){
        Map<String,AuditDailyStatisticsVo> map=new HashMap<>();
//        for (AuditType auditType:AuditType.values()) {
//            map.put(auditType.getName(),null);
//        }
        for (AuditStatistics auditStatistics:list) {
            if(auditStatistics.getAccessType()!=null ){
                AuditDailyStatisticsVo auditVo=map.get(auditStatistics.getAccessType());
                if(auditVo==null){
                    auditVo=new AuditDailyStatisticsVo();
                    auditVo.setAccessType(auditStatistics.getAccessType());
                    auditVo.setStatisticDate(DateUtils.format(new Date(),DateUtils.YYYYMMDDHHMMSSSSS));
                }
                if(auditStatistics.getIsAllowed()!=null && auditStatistics.getIsAllowed().equals(OpConstant.SUCCESS)){
                    auditVo.setSuccessCount(auditStatistics.getCount());
                }else{
                    auditVo.setFilureCount(auditStatistics.getCount());
                }
                map.put(auditStatistics.getAccessType(),auditVo);
            }
        }
        return map;
    }
}
