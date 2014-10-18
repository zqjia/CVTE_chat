package com.example.chatclient;

import java.util.Random;
import com.example.bean.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChatroomLoginActivity extends Activity 
{
	private EditText edit_ip;
	private EditText edit_port;
	private EditText edit_name;
	private Button login;
	private Button cancel;
	
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    
    private String userName;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.chatroom_login);
		
		preferences = this.getSharedPreferences("userInfo",MODE_PRIVATE);
		editor = preferences.edit();	
		
		this.edit_ip = (EditText)this.findViewById(R.id.ip);
		this.edit_port = (EditText)this.findViewById(R.id.port);
		this.edit_name = (EditText)this.findViewById(R.id.name);
		
		this.login = (Button)this.findViewById(R.id.login_chatroom);
		this.cancel = (Button)this.findViewById(R.id.cancel_chatroom);
		
		//生成该用户的实体数据
		userName = preferences.getString("userName", "");
		if(userName != "" )
		{
			this.edit_name.setText(userName);
		}
		
		
		this.login.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				final String ip = edit_ip.getText().toString().trim();
				final String port = edit_port.getText().toString().trim();
				final String name = edit_name.getText().toString();
				if(ip.equals("") || ip == null || 
						port.equals("") || port == null || 
						name.equals(""))
				{
					Toast.makeText(ChatroomLoginActivity.this, R.string.tip_input, Toast.LENGTH_SHORT).show();
					return;
				}
				
				User.setName(name);
				
				editor.putString("userName", User.getName());
				editor.commit();
				
				Intent intent = new Intent();
				
				intent.putExtra("ip", ip);
				intent.putExtra("port", port);
				intent.putExtra("name", name);
				
				intent.setClass(ChatroomLoginActivity.this, ChatroomActivity.class);
				
				startActivity(intent);			
			}
			
		});
	}

}
