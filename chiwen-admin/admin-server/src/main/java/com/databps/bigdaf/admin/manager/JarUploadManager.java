package com.databps.bigdaf.admin.manager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Yangfan on 17-9-7.
 */

@Component
public class JarUploadManager {

    @Autowired
    private File calcLauncherDir;

    private static Log log = LogFactory.getLog(JarUploadManager.class);

    public String uploadFile(HttpServletRequest request, MultipartHttpServletRequest multiReq) throws IOException {
        String uploadFilePath = multiReq.getFile("file").getOriginalFilename();
        String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
        String parent = calcLauncherDir.getParent().toString();
        log.info("parent dir is >>>>>>>>>" + parent);
        String pathDir = parent + "/data/job_jar/";
        if (!StringUtils.isEmpty(parent)) {
            File jarDir = new File(pathDir);
            if (!jarDir.exists()) {
                jarDir.mkdirs();
            }
        }
        String uploadFileContext = new File(uploadFileName + ".") + uploadFileSuffix;
        byte[] bytes = multiReq.getFile("file").getBytes();
        BufferedOutputStream buffStream =
                new BufferedOutputStream(new FileOutputStream(new File(pathDir + uploadFileContext)));
        buffStream.write(bytes);
        buffStream.close();
        return pathDir + uploadFileContext;
    }
}
