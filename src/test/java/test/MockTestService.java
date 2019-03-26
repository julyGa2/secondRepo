package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock ;
import org.mockito.MockitoAnnotations;

import com.ga2board.main.dao.PostDAO;
import com.ga2board.main.domain.Post;
import com.ga2board.main.service.impl.PostServiceImpl;

public class MockTestService {
	
	@Mock
	private PostDAO postDao;
	
	@Mock
	private Post post; 
	
	@InjectMocks
	private PostServiceImpl postService;
	
	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull(postDao);
		assertNotNull(post);
	}
	
	@Test
	public void testAllPostId() {
		int allPostId = 20;
		when(postDao.postListCount()).thenReturn(allPostId);
		
		int result = postService.listCount();
		assertEquals(result, 20);
	}
	
	@Test
	public void testpostWriterCheck() {
		String writer = "피피카";
		String password = "5656";
		String postId = "p0021";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("writer", writer);
		map.put("postId", postId);
		
		when(postDao.selectPassword(map)).thenReturn(password);
		boolean result = postService.postWriterCheck("피피카", "5656", postId);

		assertEquals(true, result);
	}
}
