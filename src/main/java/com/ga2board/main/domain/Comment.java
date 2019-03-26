package com.ga2board.main.domain;

import java.util.Date;

public class Comment {
	
	private String id;
	private String postId;
	private int level;
	private String parentId;
	private String contents;
	private String password;
	private Date date;
	private String writer;
	private Date updateDate;
	private String updateWriter;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPostId() {
		return postId;
	}
	
	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getWriter() {
		return writer;
	}
	
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getUpdateWriter() {
		return updateWriter;
	}
	
	public void setUpdateWriter(String updateWriter) {
		this.updateWriter = updateWriter;
	}

	@Override
	public String toString() {
		return this.id + " ," + this.postId + " ," + this.level + " ," + this.parentId + " ," + this.date + " ," + this.contents ;
	}
}
