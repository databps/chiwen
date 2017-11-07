package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.JobInfos;
import com.databps.bigdaf.admin.manager.JarUploadManager;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.impl.SendboxServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by Yangfan on 17-6-26.
 */

@Controller
@RequestMapping(value = "/security/sendbox")
public class SendboxController {

    private Map<String, Object> omap;

    private static Log log = LogFactory.getLog(SendboxController.class);


    @Autowired
    private SendboxServiceImpl SendboxServiceImpl;
    @Autowired
    private JarUploadManager jarUploadManager;


    @RequestMapping(value = "/newJob", method = RequestMethod.GET)
    @Secured(AuthoritiesConstants.ADMIN)
    public String returnSubJob() {
        return "sendbox/jobnew";
    }

    @Deprecated
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    @Secured(AuthoritiesConstants.ADMIN)
    public String test() {
        return "sendbox/job_bro";
    }

    @RequestMapping(value = "/searchJob", method = RequestMethod.POST)
    @Secured(AuthoritiesConstants.ADMIN)
    public String searchJob(Model model, @RequestParam(required = false) String searchJob) throws IOException, TimeoutException {
        /** @RequestParam(value="fid", required = false) String fid,
         *    对任务全部搜索
         */
        List<JobInfos> lJobInfos = null;
        lJobInfos = SendboxServiceImpl.searchJob(searchJob);
        model.addAttribute("jobInfo", lJobInfos);
        model.addAttribute("searchJob" ,searchJob);
        return "sendbox/job_bro";
    }

    /**
     * @監控所有JOB 登陆界面 / list All job
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @Secured(AuthoritiesConstants.ADMIN)
    public String monitorAllJob(Model model) {

        List<JobInfos> lJobInfos = null;
        try {
            lJobInfos = SendboxServiceImpl.monitorAlljob();
        } catch (IOException e) {
            model.addAttribute("errors", "您还没有完成配置.");
            e.printStackTrace();
        }
        model.addAttribute("jobInfo", lJobInfos);
        return "sendbox/job_bro";
    }


    @RequestMapping(value = "/formParams", method = RequestMethod.POST)
    @Secured(AuthoritiesConstants.ADMIN)
    public String getParameter(Model model,
                               @RequestParam(required = true) String jName,
                               @RequestParam(required = true) String inPath,
                               @RequestParam(required = true) String outPath,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               MultipartHttpServletRequest multiReq) throws IOException, TimeoutException {
        omap = new HashMap<>();
        String savejarPath = jarUploadManager.uploadFile(request, multiReq);
        omap.put("jName", jName);
        omap.put("jarPath", savejarPath);
        omap.put("inputPath", inPath);
        omap.put("outputPath", outPath);
        // 執行MapReduce 進程
        SendboxServiceImpl.montage(omap);
        return "redirect:index";
    }
}
