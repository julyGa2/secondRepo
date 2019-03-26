package com.ga2board.main.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/context-*.xml")

public class MockTestUnit {
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(new postController()).build();
		
	}
	
	@Test
	public void testPostController() throws Exception{
		mockMvc.perform(get("/nextPost.do").param("id", "p0001").param("searchText", "ga2"))
				.andExpect(status().isOk())
				.andExpect(view().name("main/postDetail"));
	}
	
	
}
