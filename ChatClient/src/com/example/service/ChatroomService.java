package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import org.json.JSONObject;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.example.bean.StreamTool;
import com.example.constant.ContentFlag;
import com.example.constant.MessageColums;
import com.example.bean.FormatDate;
import com.example.bean.MessageForChatroom;
import com.example.bean.User;


public class ChatroomService {
	
	private static final String TAG = "ChatroomService";
	
	private SocketAddress socAddress;
	private DataOutputStream output = null;
	private DataInputStream input = null;
	private Socket socket = null;
	private Handler handler;
	
	/**
	 * 登陆用户到聊天室
	 * @param ip
	 * @param port
	 * @param name
	 * @throws IOException 
	 * @throws Exception 
	 */
	 public void LoginChatroom(String ip, String port, String name, Handler handler) throws IOException
	 {
		 this.handler = handler;
		 
		 try {
				String flagLine = ContentFlag.CHATROOM_LOGIN_FLAG + name;
				socAddress = new InetSocketAddress(InetAddress.getByName(ip), Integer.parseInt(port)); 
				socket = new Socket();
				socket.connect(socAddress, 3000);
				output = new DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(socket.getInputStream());

				output.writeUTF(flagLine);
			}
		 catch (IOException e) 
		 {
			 throw new IOException("fail connect to the server");
		 } 
		 
		 receiveMsg(this.handler);
	 }
	 
	 public void receiveMsg(Handler handler) throws IOException 
	 {
		 try 
		 {
			while(true)
			{
				String msgCtn = input.readUTF();
				String receive_type = null;
				MessageForChatroom msg = new MessageForChatroom();
				JSONObject jsonObject = new JSONObject(msgCtn);
				try 
				 {					
					receive_type = jsonObject.getString(MessageColums.TYPE);
					String send_person = jsonObject.getString(MessageColums.SEND_PERSON);	//发送者							
					String send_date = jsonObject.getString(MessageColums.SEND_DATE);		//发送时间				
					msg.setName(send_person);				
					msg.setDate(send_date);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
				
				if(receive_type.equals("chatroom_login"))
				{					
					String send_ctn = jsonObject.getString(MessageColums.SEND_CTN);	//发送内容
					msg.setContent(send_ctn);
					
					Message message = handler.obtainMessage();
					message.what = 1;
					Bundle bundle = new Bundle();
					bundle.putSerializable("message", msg);
					message.setData(bundle);
					handler.sendMessage(message);
				}
				else if(receive_type.equals("chatroom_text_msg"))
				{
					String send_ctn = jsonObject.getString(MessageColums.SEND_CTN);	//发送内容
					msg.setContent(send_ctn);
					
					Message message = handler.obtainMessage();
					message.what = 2;
					Bundle bundle = new Bundle();
					bundle.putSerializable("message", msg);
					message.setData(bundle);
					handler.sendMessage(message);
				}
				else if(receive_type.equals("chatroom_image_msg"))
				{
					int totalSize = Integer.parseInt(jsonObject.getString(MessageColums.SEND_SIZE));
					Log.d(TAG, "total size:" + totalSize);
					
					byte[] data = StreamTool.readStream(input, totalSize);
					Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
										
				    if(bmp != null)
				    {
				    	msg.setImage(bmp);
				    }
				    
				    Message message = handler.obtainMessage();
				    Bundle bundle = new Bundle();
				    bundle.putSerializable("message", msg);
					message.what = 3;
					message.setData(bundle);
					message.sendToTarget();
				}
			}
		 }
		 catch (Exception e) 
		 {
			if (!socket.isClosed())
			{
				throw new IOException("fail connect to the server");
			}
		}
	 }
	 
	 public void sendMsg(String msg)
	 {
		 String send_person = User.getName();
		 String send_ctn = msg;
		 String send_date = FormatDate.getCurDate(); 
		 
		 StringBuilder json = new StringBuilder();
		 
		 json.append("{");
		 json.append("\"send_type\":\"").append("chatroom_text_msg")
			 .append("\",\"send_person\":\"").append(send_person)
		     .append("\",\"send_ctn\":\"").append(send_ctn)
			 .append("\",\"send_date\":\"").append(send_date);			
		json.append("\"}");
		
		Log.d(TAG, "json:" + json);
		
		try {
			this.output.writeUTF(ContentFlag.CHATROOM_TEXT_MSG_FLAG + json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void sendPic(Bitmap pic)
	 {
		 String send_person = User.getName();
		 String send_date = FormatDate.getCurDate(); 
		 
		 StringBuilder json = new StringBuilder();
		 
		 ByteArrayOutputStream byteoutput = new ByteArrayOutputStream();
		 pic.compress(Bitmap.CompressFormat.PNG, 100, byteoutput);
		 byte datas[] = byteoutput.toByteArray();
		 int send_size = datas.length;
		 Log.d(TAG, "send size is " + send_size);
		 
		 json.append("{");
		 json.append("\"send_type\":\"").append("chatroom_image_msg")
		 	 .append("\",\"send_size\":\"").append(send_size)
			 .append("\",\"send_person\":\"").append(send_person)
			 .append("\",\"send_date\":\"").append(send_date);			
		json.append("\"}");
		
		Log.d(TAG, "json:" + json);
		
		try {
			this.output.writeUTF(ContentFlag.CHATROOM_IMAGE_MSG_FLAG + json.toString());
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {

			this.output.write(datas);
			this.output.flush();
			Log.d(TAG, "finish send");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	 }
	
}

