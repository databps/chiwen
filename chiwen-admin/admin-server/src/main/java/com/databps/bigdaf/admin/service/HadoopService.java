package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo.BrowseVo;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.List;

/**
 * @author shibingxin
 * @create 2017-08-11 上午9:58
 */
public interface HadoopService {

  JsonElement getHadoopJmx(String cmpyId) throws IOException;

  List<BrowseVo> getHadoopFileList(String cmpyId,String path) throws IOException;

  String  getHdfsHost(String cmpyId) throws IOException;

  List<BrowseVo> getHadoopFileList(String cmpyId, String path,String hdfsPrincipal,String hdfsKeytabPath,String krb5confPath) throws IOException;
}