package com.example.present;

import com.example.present.service.PlayMusic;
import com.example.present.views.FallingView;
import com.example.present.views.FallingView2;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.Layout;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	public static int screenWidth,screenHeight;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }   
        //开启背景音乐
        Intent intent = new Intent(MainActivity.this,PlayMusic.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }


    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unbindService(conn);
    	super.onDestroy();
	}


	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub      
        
        //草坪
		ImageView grosslandView = (ImageView)findViewById(R.id.grassland);
    	grosslandView.setBackgroundResource(R.drawable.grassanimation);            
        AnimationDrawable grosslandAnim = (AnimationDrawable) grosslandView.getBackground();
        grosslandAnim.start();
        
        //风车
        ImageView fengcheView = (ImageView)findViewById(R.id.fengche);
        fengcheView.setBackgroundResource(R.drawable.animationfengche);            
        AnimationDrawable fengcheAnim = (AnimationDrawable) fengcheView.getBackground();
        fengcheAnim .start();
        
        //我的移动动画
        ImageView meWalkView = (ImageView)findViewById(R.id.me);
        meWalkView.setBackgroundResource(R.drawable.walkanim);            
        AnimationDrawable mewalkAnim = (AnimationDrawable) meWalkView.getBackground();       
        mewalkAnim .start();
       
    	super.onWindowFocusChanged(hasFocus);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
		}
	};
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
    	private Handler mHandler;
    	private int meWidth, meWrapWidth;
    	private boolean move = true;
    	boolean canConfess = false;
    	
    	public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);                                                           
            LinearLayout fallingViewParent = (LinearLayout)rootView.findViewById(R.id.fallingparent);
            final LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);                       
            final LinearLayout meWrap = (LinearLayout)rootView.findViewById(R.id.mewrap);            
            final ImageView me = (ImageView)rootView.findViewById(R.id.me);                        
            final ImageView flower = (ImageView)rootView.findViewById(R.id.sendflower);
            //花变心动画
            flower.setOnTouchListener(new OnTouchListener() {
				int count = 0;
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					flower.setBackgroundResource(R.drawable.hearanim);
					AnimationDrawable grosslandAnim1 = (AnimationDrawable) flower.getBackground();			    					
					grosslandAnim1.start();  
					if(++count==2){
						PendingIntent intent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(), 0);
						SmsManager.getDefault().sendTextMessage("18328786364", null, "I Love You too!", intent, null);
					}
					return false;
				}
			});
            
            //设置故事情节
            final String taleArray[] = getResources().getStringArray(R.array.faretale);
            final String confessArray[] = getResources().getStringArray(R.array.confesslove);
            final TextView tale = (TextView)rootView.findViewById(R.id.showingtext);            
            
            me.setOnTouchListener(new OnTouchListener() {
				boolean canWalk = false;
				
				int count = 0;
				int allTaleCount = taleArray.length;
				int allConfessCount = confessArray.length + allTaleCount;
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					//in case of touched more than once.
					if(meWidth==0){
						meWidth = me.getWidth();
						meWrapWidth = meWrap.getWidth();
					}
					//讲述王子和公主的故事
					if(count < allTaleCount)
						tale.setText(taleArray[count++]);
					else if(count == allTaleCount){ 
						tale.setVisibility(View.GONE);
						canWalk = true;
					}
					//讲完就移动
					if(canWalk) {						
						canWalk = false;		//只启动一个线程，不然会加速！				
						new MyThread().start();																														
					}					
					//移动玩就表白
					if(canConfess){
						if(allTaleCount <= count && count< allConfessCount){							
							tale.setVisibility(View.VISIBLE);
							tale.setText(confessArray[count-allTaleCount]);
							count++;
						}else {
							tale.setVisibility(View.GONE);
						}
					}
					return false;
				}
			});
            
            mHandler = new Handler(){
            	private int leftPadding = 0;
    			
    			@Override
    			public void handleMessage(Message msg) {
    				// 移动me            		
    				//Log.i("PlaceholderFragment","screenW:"+MainActivity.screenWidth+"meWrapWith:"+meWrapWidth+"meWith:"+meWidth);
            		leftPadding++;  
    				if(meWrapWidth+leftPadding < screenWidth-meWidth/2)
    					meWrap.setPadding(leftPadding, 0, 0, 0);
    				else{    					
    					move = false; //跳出线程
    					canConfess = true;
    					AnimationDrawable grosslandAnim = (AnimationDrawable) me.getBackground();
    			        grosslandAnim.stop();    			        
    			        
    			        flower.setVisibility(View.VISIBLE);
    			        tale.setVisibility(View.VISIBLE);
    			        Toast.makeText(getActivity(), getResources().getString(R.string.putaway), Toast.LENGTH_LONG).show();    			        
    				}
    				
    				super.handleMessage(msg);
    			}        	
            };
            
            AnimationSet aniSet = new AnimationSet(true);
            Animation rotateAnimation = AnimationUtils.loadAnimation(getActivity(), R.drawable.rotating);
            rotateAnimation.setRepeatMode(Animation.RESTART);
            Animation translateAnimation = new TranslateAnimation(0, 100.0f,0,800.0f);
            translateAnimation.setDuration(5000);
            aniSet.addAnimation(rotateAnimation);
            aniSet.addAnimation(translateAnimation);
           // aniSet.setRepeatMode(Animation.REVERSE);            
            
            params.setMargins(100, 100, 0, 0);            
            addImage(fallingViewParent, R.drawable.flower1, params ,aniSet);
            
            params.setMargins(100, 0, 0, 0);            
            addImage(fallingViewParent, R.drawable.leaf2_1, params ,null);
            
            params.setMargins(50, 0, 0, 0);            
            addImage(fallingViewParent, R.drawable.leaf2, params,null);
                                    
            return rootView;
        }
        
        class MyThread extends Thread {

			@Override
			public void run() {
				while(true){
					try {
						this.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(move)
						mHandler.sendEmptyMessage(0x000);
					else 
						break;
				}
				super.run();
			}        	
        }
        //增加落叶
        private void addImage(LinearLayout fallingViewParent, int imageId, LayoutParams params, AnimationSet aniSet){
        	final FallingView fallingView = new FallingView(getActivity(),null,imageId);
            fallingView.setAnimationSet(aniSet);
            
            fallingViewParent.addView(fallingView,params);
            fallingView.setOnTouchListener(new OnTouchListener() {
    			
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    				fallingView.startAnimation();
    				//Toast.makeText(getActivity(), "leaf touched!!", Toast.LENGTH_LONG).show();
    				return false;
    			}
    		});
        }
    }
}
