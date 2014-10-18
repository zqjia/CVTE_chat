package com.example.chatclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity 
{

	private static final String TAG = "MainActivity";
	private Button chatroomLoginButton;
	private Button loginButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
    }

    private void init()
    {
    	this.chatroomLoginButton = (Button)this.findViewById(R.id.chatroom_login_btn);
    	this.loginButton = (Button)this.findViewById(R.id.login_btn);
    	
    	this.chatroomLoginButton.setOnClickListener(new OnClickListener()
    	{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ChatroomLoginActivity.class);
				startActivity(intent);
			}
    		
    	});
    	
    	this.loginButton.setOnClickListener(new OnClickListener()
    	{

			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(MainActivity.this, ChatLoginActivity.class);
//				startActivity(intent);
			}
    		
    	});
    }
       
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        return super.onOptionsItemSelected(item);
    }
}
