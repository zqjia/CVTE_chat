package com.example.bean;

import android.graphics.Bitmap;

public class User {
	private static String name;
	private static int headImage;
	
	public static void setName(String _name)
	{
		name = _name;
	}
	
	public static String getName()
	{
		return name;
	}
	
	public static void setHeadImage(int _headImage)
	{
		headImage = _headImage;
	}
	
	public static int getHeadImage()
	{
		return headImage;
	}
}
