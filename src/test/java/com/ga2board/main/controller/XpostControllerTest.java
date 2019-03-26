package com.ga2board.main.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;						//MockMvc의 perform()메서드를 이용해서 요청 생성.
																												//MockMvcRequestBuilders의 static 메서드를 이용해서 요청 생성.
																												//코드 가독성을 위해 static import 사용


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/context-*.xml")						//사용할 스프링 설정 지정
@WebAppConfiguration
@Sql(scripts="")
public class XpostControllerTest {
//	/ga2board/src/main/resources/spring/context-common.xml
	@Autowired private WebApplicationContext ctx;
	private MockMvc mockMvc;											//MockMvc는 DispatcherServlet과 동일하게 동작.
	
	@Before
	public void setUp() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		//단일 컨트롤러에 대한 단위 테스트 목적
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/main/");
		viewResolver.setSuffix(".jsp");
//		
		mockMvc = MockMvcBuilders.standaloneSetup(new postController()).setViewResolvers(viewResolver).build();
	}
	
	private DataSource ds ;

	@Test
	public void testConection()throws Exception{try(Connection con = ds.getConnection()){System.out.println(con);}catch(Exception e){e.printStackTrace();}}
//
//	@Test
//	public void testPostDetail() throws Exception{
////		Criteria cri = new Criteria();
//		
////		mockMvc.perform(get("/listPage.do").param("cri",cri)).andExpect(status.isOk());
////		mockMvc.perform(get("/listPage.do")).andDo(print()).andExpect(status().isOk());
//		mockMvc.perform(get("/postDetail.do").param("id","p0002").param("searchText", ""))
//						.andDo(print())
//						.andExpect(status().isOk())
//						.andExpect(view().name("postDetail"));
//	}

}
