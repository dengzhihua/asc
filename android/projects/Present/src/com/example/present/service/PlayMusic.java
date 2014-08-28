package com.example.present.service;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

import com.example.present.R;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlayMusic extends Service {

	private MediaPlayer mPlayer;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		mPlayer.start();
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		AssetManager am = null;
		am = getAssets();		
		try {
			AssetFileDescriptor fileDescriptor = am.openFd("birthday_song.mp3");
			mPlayer = new MediaPlayer();
			mPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
					fileDescriptor.getStartOffset(),
					fileDescriptor.getLength());		
			mPlayer.prepare();
			mPlayer.setLooping(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.onCreate();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		mPlayer.stop();
		return super.onUnbind(intent);
	}


}
