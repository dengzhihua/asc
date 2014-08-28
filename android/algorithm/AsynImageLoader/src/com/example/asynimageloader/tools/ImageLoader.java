package com.example.asynimageloader.tools;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;

public class ImageLoader {
	public HashMap<String,SoftReference<Drawable>> imageCache;
	private ExecutorService executorService;
	private final Handler handler = new Handler();
	
	public ImageLoader(){
		executorService = Executors.newFixedThreadPool(5);
		imageCache = new HashMap<String,SoftReference<Drawable>>();
	}
	
	public Drawable loadDrawable(final String imageUrl, final ImageCallback ballback){
		if(imageCache.containsKey(imageUrl)){
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if(softReference.get() != null){
				return softReference.get();
			}
		}
		
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				final Drawable drawable = loadImage(imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ballback.imageLoaded(drawable);
					}
				});				
			}
		});
		
		return null;
	}
	
	private Drawable loadImage(String imageUrl) {
		//？
		SystemClock.sleep(2000);
		
		try {
			return Drawable.createFromStream(new URL(imageUrl).openStream(), "image.png");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//让调用者根据自己的用途去实现
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable);
	}
}
