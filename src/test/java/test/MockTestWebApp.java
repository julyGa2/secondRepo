package test;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ga2board.main.controller.postController;
import com.ga2board.main.dao.PostDAO;
import com.ga2board.main.domain.Attachment;
import com.ga2board.main.domain.Post;
import com.ga2board.main.service.impl.PostServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/context-*.xml")
@WebAppConfiguration
public class MockTestWebApp {
	
	
	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mockMvc;
	
	@Mock
	private PostDAO postDao;
	
	@Mock
	private PostServiceImpl postService;
	
	@InjectMocks
	private postController controller;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		
	}
	
	@Test
	public void testPostController() throws Exception{
		Post mock = Mockito.mock(Post.class);
		List<Attachment> list = new ArrayList<>();
		mock.setAttList(list);		
		
		Post post = new Post();
		post.setId("p0001");
		post.setAttList(list);
		
		when(postService.findPostById("p0001")).thenReturn(post);
		when(mock.getAttList()).thenReturn(list);
		
		
		
		mockMvc.perform(get("/modify.do").param("id", "p0001").param("searchText", "ga2"))
				.andExpect(status().isOk())
				.andExpect(view().name("main/postModify"))
				.andExpect(model().attribute("post", post))
				.andExpect(model().attributeExists("searchText"));

	}
	
	
}
