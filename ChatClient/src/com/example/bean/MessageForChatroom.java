package com.example.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

public class MessageForChatroom  implements Serializable{

	private String name;
	private String text_content;
	private Bitmap image_content;
	private String date;
	
	public MessageForChatroom()
	{
		name = null;
		text_content = null;
		image_content = null;
		date = null;
	}
	
	public MessageForChatroom(String name, String content, String date)
	{
		this.name = name;
		this.text_content = content;
		this.date = date;
		image_content = null;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setContent(String content)
	{
		this.text_content = content;
	}
	
	public String getContent()
	{
		return this.text_content;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public void setImage(Bitmap bitmap)
	{
		this.image_content = bitmap;
	}
	
	public Bitmap getImage()
	{
		return this.image_content;
	}
}
