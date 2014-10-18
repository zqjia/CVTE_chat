package com.cvte.constant;

/**
 * 内容标识类，区分不同的消息类型
 * @author Administrator
 *
 */
public class ContentFlag {
	
	public static final String CHATROOM_LOGIN_FLAG = "chatroom_login:";
	public static final String CHATROOM_TEXT_MSG_FLAG = "chatroom_text_msg:";
	public static final String CHATROOM_IMAGE_MSG_FLAG = "chatroom_image_msg:";
	
	public static final String ONLINE_FLAG = "online:";		//在线标识
	public static final String OFFLINE_FLAG = "offline:";	//离线标识
	public static final String REGOSTER_FLAG = "register:";	//注册标识
	public static final String RECORD_FLAG = "record:";		//语音标识
}
