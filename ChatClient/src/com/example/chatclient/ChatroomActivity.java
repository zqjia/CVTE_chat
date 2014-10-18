package com.example.chatclient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.example.bean.MessageForChatroom;
import com.example.service.ChatroomService;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChatroomActivity extends Activity 
{
	private static final String TAG = "CharroomActivity";
	private ListView listView;
	private EditText cntSend;
	private Button btnSend;
	private Button btnPic;
	private ArrayList<MessageForChatroom> itemList = new ArrayList<MessageForChatroom>();
	private ChatroomService chatroomService = new ChatroomService();
	private File cameraFile;
	private static final int REQUE_CODE_CAMERA = 1;
	private static final int REQUE_CODE_CROP = 2;
	private Bundle bundle;
	private ChatMsgAdapter adapter;
		
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
		{
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{
			case 1:
				//此时接收到登陆消息，更新界面
				bundle = msg.getData();		
				MessageForChatroom messageLog = (MessageForChatroom)bundle.getSerializable("message");
				itemList.add(messageLog);
				adapter.notifyDataSetChanged();				
				break;
			case 2:
				//此时接收到登陆消息，更新界面
				bundle = msg.getData();	
				MessageForChatroom messageChatText = 
						(MessageForChatroom)bundle.getSerializable("message");
				itemList.add(messageChatText);
				adapter.notifyDataSetChanged();
				break;
			case 3:			
				bundle = msg.getData();		
				MessageForChatroom messageChatImage = (MessageForChatroom)bundle.getSerializable("message");
				itemList.add(messageChatImage);
				adapter.notifyDataSetChanged();
				break;
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatroom);

		this.listView = (ListView)this.findViewById(R.id.lv_message);
		this.cntSend = (EditText)this.findViewById(R.id.content);
		this.btnSend = (Button)this.findViewById(R.id.sendMsg);
		this.btnPic = (Button)this.findViewById(R.id.sendPic);
		
		//这里处理登陆服务器
		Intent intent = getIntent();
		final String ip = intent.getStringExtra("ip");
		final String port = intent.getStringExtra("port");
		final String name = intent.getStringExtra("name");
		
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					chatroomService.LoginChatroom(ip, port, name, handler);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}).start();
		
		
		adapter = new ChatMsgAdapter(this, this.itemList);
		listView.setAdapter(adapter);
			
		this.btnSend.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String msg = cntSend.getText().toString();
				chatroomService.sendMsg(msg);
				cntSend.setText("");
			}
			
		});
		
		this.btnPic.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File fileDir = null;
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
				{
					fileDir = new File(Environment.getExternalStorageDirectory()+"/user_photos"); 
					if(!fileDir.exists())
					{  
						fileDir.mkdirs();  
					}  
				}
				else
				{
					Toast.makeText(ChatroomActivity.this, R.string.sd_noexit, Toast.LENGTH_SHORT);
					return;
				}
				cameraFile = new File(fileDir.getAbsoluteFile()+"/"+System.currentTimeMillis()+".jpg");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
				startActivityForResult(intent, REQUE_CODE_CAMERA);
			}
			
		});
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) 
		{
			switch (requestCode) 
			{
			case REQUE_CODE_CAMERA:
				startPhotoZoom(Uri.fromFile(cameraFile));
				break;
			case REQUE_CODE_CROP:
				Bitmap cropBitmap = (Bitmap)data.getParcelableExtra("data");
				if(cropBitmap!= null){
					chatroomService.sendPic(cropBitmap);
				}
				break;
			default:
				break;
			}
		}
	}
	
	private void startPhotoZoom(Uri uri) 
	{
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQUE_CODE_CROP);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
		System.exit(0);
	}
	
	
}
