//package com.databps.bigdaf.admin;
//
//import org.front.server.Application;
//import org.front.server.web.control.TestController;
//import org.hamcrest.Matchers;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
////网上很多会在这里使用import static,主要导入的是MockMvcRequestBuilders，MockMvcResultMatchers，Matchers这三个类中的方法。
//
//
///**
// * @author zz
// * @date 2017年7月4日
// *
// */
//@RunWith(SpringJUnit4ClassRunner.class)
////@SpringApplicationConfiguration(classes = MockServletContext.class)//这个测试单个controller，不建议使用
////@SpringApplicationConfiguration(classes = Application.class)//这里的Application是springboot的启动类名。
////@WebAppConfiguration
//@SpringBootApplication
//public class ApplicationTests {
//    @Autowired
//    private WebApplicationContext context;
//    private MockMvc mvc;
//
//    @Before
//    public void setUp() throws Exception {
//        //       mvc = MockMvcBuilders.standaloneSetup(new TestController()).build();
//        mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
//    }
//    @Test
//    public void test1() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/data/getMarkers")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .param("lat", "123.123").param("lon", "456.456")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));
//
//    }
//}