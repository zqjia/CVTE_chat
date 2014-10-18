package com.cvte.bean;

/**
 *消息pojo
 * @author Administrator
 *
 */
public class Message {
	private String send_ctn;

	public Message(String send_ctn, String send_person, String send_date)
	{
		super();
		this.send_ctn = send_ctn;
	}
	public String getSend_ctn() {
		return send_ctn;
	}
	public void setSend_ctn(String send_ctn) {
		this.send_ctn = send_ctn;
	}


	
}
