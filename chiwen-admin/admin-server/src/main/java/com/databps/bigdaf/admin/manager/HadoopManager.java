package com.databps.bigdaf.admin.manager;

import com.databps.bigdaf.admin.config.KerberosConfig;
import com.databps.bigdaf.admin.dao.ConfigDao;
import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.service.HadoopService;
import com.databps.bigdaf.admin.service.KerberosService;
import com.databps.bigdaf.admin.vo.BrowseVo;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author shibingxin
 * @create 2017-08-12 上午10:41
 */
@Service
public class HadoopManager {

  @Autowired
  private KerberosService kerberosService;
  @Autowired
  private HadoopService hadoopService;
  @Autowired
  private ConfigDao configDao;

  public static final int KERBEROS_OPEN = 1;

  public List<BrowseVo> getHadoopFileList(String cmpyId, String path) throws IOException {
    List<BrowseVo> result = null;
    Config config  = configDao.findConfig(cmpyId);
    if(config.getKerberosEnable() ==KERBEROS_OPEN ) {
      String hdfsPrincipal = kerberosService.getHdfsPrincipal(cmpyId);
      String hdfsKeytabPath = kerberosService.getHdfsKeytabPath(cmpyId);
      String krb5confPath =  kerberosService.getKrb5ConfPath(cmpyId);
      result = hadoopService.getHadoopFileList(cmpyId,path,hdfsPrincipal,hdfsKeytabPath,krb5confPath);
    }else{
      result = hadoopService.getHadoopFileList(cmpyId,path);
    }
    return result;
  }
}