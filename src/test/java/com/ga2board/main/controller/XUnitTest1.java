package com.ga2board.main.controller;

//import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {
        "/context-*.xml"
      })

public class XUnitTest1 {

//	  @Resource(name="postController")
	    postController postC = new postController();

	  @Test
	  public void ss() throws Exception{
		  postC.postDetail("p0001", "");
	  }
}
