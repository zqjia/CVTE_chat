package com.cvte.server;


/**
 * 服务端socket处理类
 */
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import com.cvte.bean.User;
import com.cvte.constant.ContentFlag;
import com.cvte.tools.FormatDate;
import com.cvte.tools.StreamTool;
import com.cvte.constant.MessageColums;

public class ChatServer
{
	private ExecutorService executorService;// 线程池
	private int port;// 监听端口
	private boolean quit = false;// 退出
	private ServerSocket server;
	private List<SocketTask> taskList = new ArrayList<SocketTask>();// 保存所有启动的socket集合

	
	
	public ChatServer(int port)
	{
		this.port = port;
		// 创建线程池，池中具有(cpu个数*50)条线程
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * 50);
		
	}	
	
	/**
	 * 服务器终止,关闭所有线程
	 */
	public void quit() 
	{
		this.quit = true;
		try 
		{
			for (SocketTask tast : taskList) 
			{
				tast.input.close();
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 服务器启动
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception
	{
		server = new ServerSocket(port);
		new Thread(new Runnable()
		{
			public void run()
			{
				while (!quit) {
					try {
						System.out.println("等待用户的socket请求");
						Socket socket = server.accept();
						// 为支持多用户并发访问，采用线程池管理每一个用户的连接请求
						SocketTask newTask = new SocketTask(socket);
						taskList.add(newTask);
						executorService.execute(newTask);
						System.out.println("启动一个线程开始处理socket请求");
					} catch (Exception e) {
						System.out.println("服务器终止！关闭所有线程");
					}
				}
			}
		}).start();
	}

	/**
	 * 内部线程类,负责与每个客户端的数据通信
	 * 
	 * @author Administrator
	 */
	private final class SocketTask implements Runnable 
	{
		private Socket s;
		private DataInputStream input;
		private DataOutputStream output;
		private User curUser;

		public SocketTask(Socket socket)
		{
			s = socket;
			try {
				input = new DataInputStream(s.getInputStream());
				output = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 发送消息
		 * 
		 * @param msg
		 * @param datas
		 */
		public void sendMsg(String json, byte[] datas)
		{
			try 
			{
				if (null != json) {
					output.writeUTF(json);
					output.flush();
				}
				if (null != datas) {
					output.write(datas, 0, datas.length);
					output.flush();
				}
				
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() 
		{
			try {
				while (true) 
				{
					String msgCtn = input.readUTF();
					if(msgCtn.startsWith(ContentFlag.CHATROOM_LOGIN_FLAG))
					{
						String name = msgCtn.substring(ContentFlag.CHATROOM_LOGIN_FLAG.length()).trim();
						System.out.println("user name is :" + name);
						System.out.println("当前在线的人数：" + taskList.size());
						
						String send_person = name; // 发送者
						String send_ctn = "进入聊天室！"; // 发送内容
						String send_date = FormatDate.getCurDate(); // 发送时间
						
						StringBuilder json = new StringBuilder();
						
						json.append("{");
						json.append("\"send_type\":\"").append("chatroom_login").
						append("\",\"send_person\":\"").append(send_person)
							.append("\",\"send_ctn\":\"").append(send_ctn)
							.append("\",\"send_date\":\"").append(send_date);			
						json.append("\"}");
						
						System.out.println("json:" + json);
						
						for (SocketTask tast : taskList) 
						{
							System.out.println("循环向客户端发送聊天室上线消息");
							tast.sendMsg(json.toString(), null);
						}
						
					}
					else if(msgCtn.startsWith(ContentFlag.CHATROOM_TEXT_MSG_FLAG))
					{
						System.out.println("接收到文字消息");
						String recv = msgCtn.substring(ContentFlag.CHATROOM_TEXT_MSG_FLAG.length()).trim();
						JSONObject jsonObject = new JSONObject(recv);
						String send_type = jsonObject.getString(MessageColums.TYPE);
						String send_person = jsonObject.getString(MessageColums.SEND_PERSON);	//发送者
						String send_ctn = jsonObject.getString(MessageColums.SEND_CTN);			//发送内容
						String send_date = jsonObject.getString(MessageColums.SEND_DATE);		//发送时间				
						
						StringBuilder json = new StringBuilder();
						
						json.append("{");
						json.append("\"send_type\":\"").append("chatroom_text_msg")
							.append("\",\"send_person\":\"").append(send_person)
							.append("\",\"send_ctn\":\"").append(send_ctn)
							.append("\",\"send_date\":\"").append(send_date);			
						json.append("\"}");
						
						System.out.println("json:" + json);
						
						for (SocketTask tast : taskList) 
						{
							System.out.println("循环向客户端发送好友文字消息");
							tast.sendMsg(json.toString(), null);
						}
					}
					else if(msgCtn.startsWith(ContentFlag.CHATROOM_IMAGE_MSG_FLAG))
					{
						System.out.println("接收到图片消息");
						String recv = msgCtn.substring(ContentFlag.CHATROOM_IMAGE_MSG_FLAG.length()).trim();
						JSONObject jsonObject = new JSONObject(recv);
						String send_type = jsonObject.getString(MessageColums.TYPE);
						String send_size = jsonObject.getString(MessageColums.SEND_SIZE);
						String send_person = jsonObject.getString(MessageColums.SEND_PERSON);	//发送者
						String send_date = jsonObject.getString(MessageColums.SEND_DATE);		//发送时间				
						
						System.out.println("receive send_size is " + send_size);
						
						StringBuilder json = new StringBuilder();
						json.append("{");
						json.append("\"send_type\":\"").append("chatroom_image_msg")
							.append("\",\"send_person\":\"").append(send_person)
							.append("\",\"send_size\":\"").append(send_size)
							.append("\",\"send_date\":\"").append(send_date);			
						json.append("\"}");
						
						byte[] data = StreamTool.readStream(input, Integer.parseInt(send_size));
						System.out.println("data size is " + data.length);
						StreamTool.buff2Image(data, ".\\abc.png");
						
						System.out.println("data size is " + data.length);
						
						for (SocketTask tast : taskList) 
						{
							System.out.println("循环向客户端发送好友图片消息");
							tast.sendMsg(json.toString(), data);
						}					
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				taskList.remove(this);
				System.out.println("关闭线程" + Thread.currentThread().getName());
			} finally {
				try {
					if (null != input)
						input.close();
					if (null != output)
						output.close();
					if (null != s)
						s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
