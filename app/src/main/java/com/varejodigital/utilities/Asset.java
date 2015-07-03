package com.varejodigital.utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Asset 
{
    static public boolean exists(Context context, String filePath)
    {
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(filePath);
            if (is != null) {
                is.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

	static public String readString(Context context, String filePath)
	{
		String result = "";
		AssetManager am = context.getAssets();
		try 
		{
			InputStream is = am.open(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = reader.readLine();
			while (line != null) 
			{
				result = result + line;
				line = reader.readLine();
			}
            is.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

        if ("".equals(result))
            return null;

		return result;
	}

    static public Bitmap readBitmap(Context context, String filePath)
    {
        AssetManager am = context.getAssets();
        InputStream istr;
        Bitmap bmp = null;
        try {
            istr = am.open(filePath);
            bmp = BitmapFactory.decodeStream(istr);
            istr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }

    static public byte[] readBytes(Context context, String filePath)
    {
        byte[] result = null;
        AssetManager am = context.getAssets();
        InputStream istr;
        try {
            istr = am.open(filePath);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int size = 0;
            byte[] buffer = new byte[1024];
            while((size = istr.read(buffer,0,1024)) >= 0){
                outputStream.write(buffer,0,size);
            }
            istr.close();
            result = outputStream.toByteArray();
            outputStream.close();;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

	static public void copyTo(Context context, String assetFile, OutputStream out) throws IOException 
	{
		AssetManager am = context.getAssets();
		InputStream in = am.open(assetFile);
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}
}

