package com.example.chatclient;

import java.util.ArrayList;

import com.example.bean.MessageForChatroom;
import com.example.bean.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatMsgAdapter extends BaseAdapter
{
	private Context mContext;
	private MessageForChatroom msg;
	private ArrayList<MessageForChatroom> msgList;
	private LayoutInflater mInflater;
	
	static class ViewHolder
	{
		public TextView tvTime;
		public TextView tvName;
		public ImageView ivHead;
		public TextView tvMsg;
		public ImageView ivBitmapImage;
	}
	
	class ViewHolderText 
	{
		public TextView tvTime;
		public TextView tvName;
		public ImageView ivHead;
		public TextView tvMsg;
	}
	
	class ViewHolderImage
	{
		public TextView tvTime;
		public TextView tvName;
		public ImageView ivHead;
		public ImageView ivBitmapImage;
	}
	
	public ChatMsgAdapter(Context context, ArrayList<MessageForChatroom> list)
	{
		this.mContext = context;
		this.msgList = list;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.msgList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return msgList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
//		ViewHolderText holderText = null;
//        ViewHolderImage holderImage = null;
        ViewHolder holder = null;
        
        msg = msgList.get(position);
        
        holder = new ViewHolder();
	        if (convertView == null) 
	        {
	        	if(msg.getName().equals(User.getName()))
	        	{
	        		if(msg.getImage() == null)
	            	{
	            		convertView = this.mInflater.inflate(R.layout.item_text_right, null);
	            		/*holderText = new ViewHolderText();
	            		holderText.tvTime = (TextView) convertView.findViewById(R.id.item_date);
	            		holderText.tvName =  (TextView) convertView.findViewById(R.id.user_name);
	            		holderText.tvMsg =  (TextView) convertView.findViewById(R.id.text_content);
	            		holderText.ivHead = (ImageView)convertView.findViewById(R.id.userhead);
	            		convertView.setTag(holderText);*/
	            		
	            		holder.tvTime = (TextView) convertView.findViewById(R.id.item_date);
	            		holder.tvName =  (TextView) convertView.findViewById(R.id.user_name);
	            		holder.tvMsg =  (TextView) convertView.findViewById(R.id.text_content);
	            		holder.ivHead = (ImageView)convertView.findViewById(R.id.userhead);
	            		convertView.setTag(holder);
	            	}
	            	else
	            	{
	            		convertView = this.mInflater.inflate(R.layout.item_image_right, null);
	            		/*holderImage = new ViewHolderImage();
	            		holderImage.tvTime = (TextView) convertView.findViewById(R.id.item_date);
	            		holderImage.tvName =  (TextView) convertView.findViewById(R.id.user_name);
	            		holderImage.ivBitmapImage =  (ImageView) convertView.findViewById(R.id.image_content);
	            		holderImage.ivHead = (ImageView)convertView.findViewById(R.id.userhead);
	            		convertView.setTag(holderImage);*/
	            		
	            		holder.tvTime = (TextView) convertView.findViewById(R.id.item_date);
	            		holder.tvName =  (TextView) convertView.findViewById(R.id.user_name);
	            		holder.ivBitmapImage =  (ImageView) convertView.findViewById(R.id.image_content);
	            		holder.ivHead = (ImageView)convertView.findViewById(R.id.userhead);
	            		convertView.setTag(holder);
	            	}
	        	}
	        	else
	        	{
	        		if(msg.getImage() == null)
	            	{
	            		convertView = this.mInflater.inflate(R.layout.item_text_left, null);
	            		/*holderText = new ViewHolderText();
	            		holderText.tvTime = (TextView) convertView.findViewById(R.id.item_date);
	            		holderText.tvName =  (TextView) convertView.findViewById(R.id.user_name);
	            		holderText.tvMsg =  (TextView) convertView.findViewById(R.id.text_content);
	            		holderText.ivHead = (ImageView)convertView.findViewById(R.id.userhead);
	            		convertView.setTag(holderText);*/
	            		
	            		holder.tvTime = (TextView) convertView.findViewById(R.id.item_date);
	            		holder.tvName =  (TextView) convertView.findViewById(R.id.user_name);
	            		holder.tvMsg =  (TextView) convertView.findViewById(R.id.text_content);
	            		holder.ivHead = (ImageView)convertView.findViewById(R.id.userhead);
	            		convertView.setTag(holder);
	            	}
	            	else
	            	{
	            		convertView = this.mInflater.inflate(R.layout.item_image_left, null);
	            		/*holderImage = new ViewHolderImage();
	            		holderImage.tvTime = (TextView) convertView.findViewById(R.id.item_date);
	            		holderImage.tvName =  (TextView) convertView.findViewById(R.id.user_name);
	            		holderImage.ivBitmapImage =  (ImageView) convertView.findViewById(R.id.image_content);
	            		holderImage.ivHead = (ImageView)convertView.findViewById(R.id.userhead);
	            		convertView.setTag(holderImage);*/
	            		
	            		holder.tvTime = (TextView) convertView.findViewById(R.id.item_date);
	            		holder.tvName =  (TextView) convertView.findViewById(R.id.user_name);
	            		holder.ivBitmapImage =  (ImageView) convertView.findViewById(R.id.image_content);
	            		holder.ivHead = (ImageView)convertView.findViewById(R.id.userhead);
	            		convertView.setTag(holder);
	            	}
	        	}
	        	
	        }
	        else
	        {
	        	/*if(msg.getImage() == null)
	        		holderText = (ViewHolderText)convertView.getTag();
	        	else
	        		holderImage = (ViewHolderImage)convertView.getTag();*/
	        	holder = (ViewHolder)convertView.getTag();
	        }
	        
	        if(msg != null)
	        {
		        if(msg.getName().equals(User.getName()))
		        {
		            if(msg.getImage() == null)
		            {
		        		/*holderText.tvTime.setText(msg.getDate());
		        		holderText.tvName.setText(msg.getName());
		        		holderText.tvMsg.setText(msg.getContent());
		        		holderText.ivHead.setImageResource(R.drawable.user);*/
		            	holder.tvTime.setText(msg.getDate());
		        		holder.tvName.setText(msg.getName());
		        		holder.tvMsg.setText(msg.getContent());
		        		holder.ivHead.setImageResource(R.drawable.user);
		            }
		            else
		            {
		            	/*holderImage.tvTime.setText(msg.getDate());
		            	holderImage.tvName.setText(msg.getName());
		            	holderImage.ivHead.setImageResource(R.drawable.user);
		            	holderImage.ivBitmapImage.setImageBitmap(msg.getImage());*/
		            	holder.tvTime.setText(msg.getDate());
		        		holder.tvName.setText(msg.getName());
		        		holder.ivBitmapImage.setImageBitmap(msg.getImage());
		        		holder.ivHead.setImageResource(R.drawable.user);
		            }
		        }
/*		        else
		        {
		        	 if(msg.getImage() == null)
		             {
		         		holderText.tvTime.setText(msg.getDate());
		         		holderText.tvName.setText(msg.getName() + ": ");
		         		holderText.tvMsg.setText(msg.getContent());
		         		holderText.ivHead.setImageResource(R.drawable.user);
		        		holderText.tvTime.setText(msg.getDate());
			        	holderText.tvName.setText(msg.getName());
			        	holderText.tvMsg.setText(msg.getContent());
			        	holderText.ivHead.setImageResource(R.drawable.user);
		             }
		             else
		             {
		             	holderImage.tvTime.setText(msg.getDate());
		             	holderImage.tvName.setText(msg.getName() + ": ");
		             	holderImage.ivHead.setImageResource(R.drawable.user);
		             	holderImage.ivBitmapImage.setImageBitmap(msg.getImage());
		             }
		        }	*/
	        }
			
        return convertView;
	}
	

}
