package com.example.present.views;

import com.example.present.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class FallingView2 extends View {
	
	private float positionX = 0;
	private float positionY = 0;
	private Handler mHandler;
	private WindowManager wm;
	private Bitmap DefaultBitmap;
	private Context mContext;
	public FallingView2(Context context, WindowManager wm) {
		super(context);
		this.wm = wm;
		this.mContext = context;
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
				case 0x111:
					postInvalidate();//强制更新view
					break;
				}
				super.handleMessage(msg);
			}			
		};
		
		Resources resources = getResources();		
		DefaultBitmap = BitmapFactory.decodeResource(resources, R.drawable.leaf2_1);
		// TODO Auto-generated constructor stub
	}
	

	private void setPhoneMatric(){
		
		//phoneHeight = mContext.getSystemService(Wind)
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub						
		positionX = ++positionX%wm.getDefaultDisplay().getWidth();
		positionY = ++positionY%wm.getDefaultDisplay().getHeight();
		canvas.drawBitmap(DefaultBitmap, positionX,positionY, null);
		mHandler.sendEmptyMessageDelayed(0x000,100);
		Log.i("FallingView", "positionX:"+positionX+"positionY:"+positionY);
		super.onDraw(canvas);
	}
	
	
	//设置初始位置
	public void setPosition(float x, float y){
		this.positionX = x;
		this.positionY = y;
	}
}
