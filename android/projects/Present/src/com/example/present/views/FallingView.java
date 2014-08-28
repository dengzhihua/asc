package com.example.present.views;

import com.example.present.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class FallingView extends View {
	private Context mContext;
	private float phoneWidth,phoneHeight;
	private Bitmap Defaultbitmap;
	private float positionX = 10,positionY = 20;
	private long duration = 5000;
	private boolean isFalling,isFalled = false;
	
	private Animation translateAnimation = null;
	private Animation rotateAnimation = null;
	//��������
	private Animation scaleAnimation = null,scaleAnimation2 = null;
	//�Ƿ�������ʹ��ͬһ��interpolator
	private AnimationSet  animationSet;
	
	public FallingView(Context context,AttributeSet attrs, int bitmapId){
		this(context,attrs);
		Defaultbitmap = BitmapFactory.decodeResource(getResources(), bitmapId);
	}
	
	public FallingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		Resources resources = getResources();
		animationSet = new AnimationSet(true);
		
		//��ʼ��Ĭ�϶���
		translateAnimation = new TranslateAnimation(0, 400.0f,0,600.0f);
		rotateAnimation =  new RotateAnimation(0f, 30f);   
		
		//��Ҷ�����ٶ�
		scaleAnimation = new ScaleAnimation(0.01f, 1.0f,0.01f,1.0f,1.0f,0.5f);  	 
		scaleAnimation.setDuration(5000);  
		this.startAnimation(rotateAnimation); 

		//��Ҷ����β�ٶ�
		scaleAnimation2 = new ScaleAnimation(1.0f, 0.0f,1.0f,0.0f);  	 
		scaleAnimation2.setDuration(5000);  		
		
		Defaultbitmap = BitmapFactory.decodeResource(resources, R.drawable.leaf2);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onAnimationEnd() {
		//System.out.println("animation end!");
		if(isFalling){
			isFalled = true;
			isFalling = false;
		}
		if(isFalled){
			this.startAnimation(scaleAnimation);
			isFalled = false;
		}
		super.onAnimationEnd();
	}

	private void setPhoneMatric(){
		
	}
	/*
	 * ��дonMeasure��������ͼƬʵ�ֳߴ�
	 * ����ģʽ��UNSPECIFIED��EXACTYLY��AT_MOST)
	 * 
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//�������setMeasuredDimension��Ч
		widthMeasureSpec = measureWidth(widthMeasureSpec);
		heightMeasureSpec = measureHeight(heightMeasureSpec);
		//Log.i("FallingView","width:"+widthMeasureSpec+"height:"+heightMeasureSpec);
		setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub				
		canvas.drawBitmap(Defaultbitmap, 0,0, null);
		//Log.i("FallingView", "rw:"+this.getWidth()+"rh:"+this.getHeight());
		super.onDraw(canvas);
	}
	
	//����Ҫ��ʾ��ͼƬ
	public void setBitmap(Bitmap image){
		Defaultbitmap = image;
	}
	
	//���ó�ʼλ��
	public void setPosition(float x, float y){
		this.positionX = x;
		this.positionY = y;
	}
	
	//���ö���
	public void setAnimationSet(AnimationSet aniSet){
		if(null == aniSet){
			animationSet.addAnimation(translateAnimation);
			animationSet.addAnimation(rotateAnimation);
			animationSet.setDuration(duration);
			return;
		}
		animationSet = aniSet;
	}
	public void startAnimation(){
		this.startAnimation(animationSet);
		isFalling = true;		
	}
	
	public void updateView(){		
			invalidate();		
	}
	
	private int measureWidth(int measureSpec){
		int result = 0;
		int spcMode = MeasureSpec.getMode(measureSpec);
		int spcSize = MeasureSpec.getSize(measureSpec);
		//Log.i("FallingView","spcSize:"+spcSize);
		if(spcMode==MeasureSpec.EXACTLY)				
			//we are told how big to be.
			result = spcSize;
			
		else {
			//we define the size
			result = Defaultbitmap.getWidth();
			//Log.i("FallingView","Defaultbitmap:"+Defaultbitmap.getWidth());
			if(spcMode  == MeasureSpec.AT_MOST){
				result = spcSize;
			}
		}
		return result;
	}	
	private int measureHeight(int measureSpec){
		int result = 0;
		int spcMode = MeasureSpec.getMode(measureSpec);
		int spcSize = MeasureSpec.getSize(measureSpec);
		//Log.i("FallingView","spcSize:"+spcSize);
		if(spcMode==MeasureSpec.EXACTLY)				
			//we are told how big to be.
			result = spcSize;
			
		else {
			//we define the size
			result = Defaultbitmap.getHeight();
			//Log.i("FallingView","Defaultbitmap:"+Defaultbitmap.getHeight());
			if(spcMode  == MeasureSpec.AT_MOST){
				result = spcSize;
			}
		}
		return result;
	}
}
