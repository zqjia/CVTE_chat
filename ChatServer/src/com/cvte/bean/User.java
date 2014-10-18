package com.cvte.bean;

/**
* 用户pojo
* 
* @author Administrator
* 
*/
public class User {
	private long id; // 主键ID
//	private String ip; // 连接服务器的ip地址
//	private String port; // 连接服务器的port号
	private String name; // 用户登录名
	private boolean isOnline = false; // 当前用户标识

//	public String getIp() {
//		return ip;
//	}
//	public void setIp(String ip) {
//		this.ip = ip;
//	}
//	public String getPort() {
//		return port;
//	}
//	public void setPort(String port) {
//		this.port = port;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getIsOnlie() {
		return isOnline;
	}
	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
