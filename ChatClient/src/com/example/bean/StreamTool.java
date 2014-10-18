package com.example.bean;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTool {
	/**
	 * 读取本地文件流
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception
	{
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			BufferedInputStream bfinput = new BufferedInputStream(inStream);
			byte[] buffer = new byte[1024];
			int len = -1;
			while( (len=bfinput.read(buffer)) != -1)
			{
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			bfinput.close();
			return outSteam.toByteArray();
	}
	
	/**
	 * 读取socket流
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readStream(DataInputStream inStream, int totalSize) throws IOException
	{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		//获取要从流中读取的数据长度       网络流数据格式   length+data
	
		byte[] buffer = new byte[totalSize];
		int len= -1;
		int readCount = 0;
		while(readCount < totalSize)
		{
			len = inStream.read(buffer, 0, totalSize - readCount);
			readCount+= len;
			outStream.write(buffer, 0, len);
		}
		byte[] datas = outStream.toByteArray();
		outStream.close();
		return datas;
	}
	
	public static void buff2Image(byte[] b,String tagSrc) throws Exception
    {

        FileOutputStream fout = new FileOutputStream(tagSrc);
        //将字节写入文件
        fout.write(b);
        fout.close();

    }

}

