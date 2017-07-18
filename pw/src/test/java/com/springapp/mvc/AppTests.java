package com.springapp.mvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring-config.xml","classpath:springmvc-servlet.xml",
		"classpath:spring/spring-apps-service.xml",
		"classpath:spring/spring-datasource.xml"})
//@ContextConfiguration(locations = {"classpath:springmvc-servlet.xml"})
public class AppTests {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void simple() throws Exception {
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))  
//                .andExpect(MockMvcResultMatchers.view().name("user/view"))  
//                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))  
//                .andDo(MockMvcResultHandlers.print())  
//                .andReturn();  
//        
//        Assert.assertNotNull(result.getModelAndView().getModel().get("user"));
        System.out.println("11");
    }
}
