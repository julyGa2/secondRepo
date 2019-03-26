package com.ga2board.main.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/context-*.xml")
@WebAppConfiguration
public class MockTestWebApp {
	
//	@Autowired
//	postController postController;
	
	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mockMvc;
	
//	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(),
//            Charset.forName("utf8"));

	
	@Before
	public void setUp() throws Exception{
//		mockMvc = MockMvcBuilders.standaloneSetup(new postController()).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		
	}
	
	@Test
	public void testPostController() throws Exception{
//		mockMvc.perform(get("/postDetail.do").param("id", "p0001").param("searchText", ""))
//				.andExpect(status().isOk());
		MvcResult mvcResult = mockMvc.perform(get("/postDetail.do").param("id", "p0001").param("searchText", ""))
										.andExpect(status().isOk())
										.andReturn();
		/**
		 * 
		 * MvcResult의 주요 메소드!!
		 * 
		 * 
		 */
	}
	
	
}
