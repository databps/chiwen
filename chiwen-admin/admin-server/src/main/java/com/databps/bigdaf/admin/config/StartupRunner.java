package com.databps.bigdaf.admin.config;

import com.databps.bigdaf.admin.dao.ConfigDao;
import com.databps.bigdaf.admin.dao.ServicesDao;
import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.admin.manager.ServicesManager;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.security.domain.Admin;
import com.databps.bigdaf.admin.service.UserService;
import com.databps.bigdaf.admin.web.controller.vm.ManagedUserVM;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author merlin
 * @create 2017-08-12 下午9:34
 */
@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    ServicesManager servicesManager;
    @Autowired
    private ServicesDao serviveDao;
    @Autowired
    private ConfigDao configDao;


    public static String cmpyId = "5968802a01cbaa46738eee3d";
    @Override
    public void run(String... args) throws Exception {

        Optional<Admin> admin = userService.findOneByLogin("admin");
        if (!admin.isPresent()) {
            ManagedUserVM managedUserVM = new ManagedUserVM();
            managedUserVM.setPassword("admin!123");
            managedUserVM.setLogin("admin");
            managedUserVM.setActivated(true);
            managedUserVM.setEmail("admin@databps.com");
            managedUserVM.setFirstName("admin");
            managedUserVM.setLastName("admin");
            managedUserVM.setImageUrl("http://placehold.it/50x50");
            managedUserVM.setLangKey("zh-cn");
            managedUserVM.setParentId("0");
            userService
                    .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
                            managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
                            managedUserVM.getLangKey(), AuthoritiesConstants.ADMIN,managedUserVM.getParentId());
        }

        Optional<Admin> auditor = userService.findOneByLogin("auditor");

        if (!auditor.isPresent()) {
            ManagedUserVM managedUserVM = new ManagedUserVM();
            managedUserVM.setPassword("admin!123");
            managedUserVM.setActivated(true);
            managedUserVM.setLogin("auditor");
            managedUserVM.setEmail("auditor@databps.com");
            managedUserVM.setFirstName("auditor");
            managedUserVM.setLastName("auditor");
            managedUserVM.setImageUrl("http://placehold.it/50x50");
            managedUserVM.setLangKey("zh-cn");
            managedUserVM.setParentId("0");
            userService
                    .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
                            managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
                            managedUserVM.getLangKey(),AuthoritiesConstants.AUDITOR,managedUserVM.getParentId());
        }
        List<Services> services = serviveDao.getServices(cmpyId);
        if (services == null || services.isEmpty()) {
            servicesManager.initServcies(cmpyId);
        }
        Config config = configDao.findConfig(cmpyId);
        if(config== null) {
            config = new Config();
            config.setCmpyId(cmpyId);
            config.setTestMode("false");
            config.setKerberosEnable(0);
            config.setLoginIntervalTime(0);
            config.setLoginMaxNumber(10);
            configDao.saveConfig(config);
        }

    }

}
