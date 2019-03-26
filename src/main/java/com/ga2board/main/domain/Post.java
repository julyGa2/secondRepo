package com.ga2board.main.domain;

import java.util.Date;
import java.util.List;

public class Post {
	
	private String id;
	private String title;
	private String password;
	private String content;
	private int view;
	private Date date;
	private String writer;
	private Date updateDate;
	private String updateWriter;
	private List<Attachment> attList;
	private List<Comment> commentList;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
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
	public List<Attachment> getAttList() {
		return attList;
	}
	public void setAttList(List<Attachment> attList) {
		this.attList = attList;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	

	@Override
	public String toString() {
		return this.id + " ," + this.title + " ," + this.view + " ," + this.date;
	}
}
