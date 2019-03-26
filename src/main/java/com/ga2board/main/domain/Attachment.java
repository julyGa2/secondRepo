package com.ga2board.main.domain;

import java.util.Date;

public class Attachment {
	
	private String id;
	private String postId;
	private String fileName;
	private String filePath;
	private String fileOrg;
	private long size;		
	private Date updateDate;
	private String updateWriter;
	private String delGB;
	
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileOrg() {
		return fileOrg;
	}
	public void setFileOrg(String fileOrg) {
		this.fileOrg = fileOrg;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
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
	public String getDelGB() {
		return delGB;
	}
	public void setDelGB(String delGB) {
		this.delGB = delGB;
	}

}
