package com.example.socketactivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.example.socketactivity.client.Client;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private final String TAG = "PlaceholderFragment";	

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        new Client();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
    	private static final String SERVERIP = "222.18.14.9";
    	private static final int SERVERPORT = 54321;
    	private Thread mThread = null;
    	private Socket msocket = null;
    	private Button mButton_LOGIN = null;
    	private Button mButton_SEND = null;
    	private EditText mEditText_SHOW = null;
    	private EditText mEditText_SEND = null;
    	private BufferedReader mBufferedReader = null;
    	private PrintWriter mPrintWriter = null;
    	private String mStrMSG = "";
    	
    	public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
           //initWidgets(rootView);
          // mThread = new Thread(mRunnable);
           //mThread.start();
           return rootView;
        }
        
        private void initWidgets(View rootView){
            mButton_LOGIN = (Button)rootView.findViewById(R.id.login);
            mButton_SEND = (Button)rootView.findViewById(R.id.send);
            mEditText_SEND = (EditText)rootView.findViewById(R.id.editText);
            mEditText_SHOW = (EditText)rootView.findViewById(R.id.showText);
            try{
            	msocket = new Socket(SERVERIP,SERVERPORT);
            	mBufferedReader = new BufferedReader(new InputStreamReader(msocket.getInputStream()));
            	mPrintWriter = new PrintWriter(msocket.getOutputStream(),true);
            }catch(Exception e){
            	//e.printStackTrace();
            	Toast.makeText(getActivity(), "socket null!", Toast.LENGTH_LONG).show();
            }
            
            mButton_LOGIN.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    			}
    		});
            mButton_SEND.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				String str = mEditText_SEND.getText().toString() + "\n";
    				mPrintWriter.print(str);
    				mPrintWriter.flush();
    			}
    		});
        }
        
        private Runnable mRunnable = new Runnable (){

    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			while(true){
    				try{
    					if((mStrMSG = mBufferedReader.readLine()) != null){
    						mStrMSG += "\n";
    						mHandler.sendMessage(mHandler.obtainMessage());
    					}
    				}catch(Exception e){
    					e.printStackTrace();
    				}
    			}
    		}    	
        };
        
        private  Handler mHandler = new Handler(){

    		@Override
    		public void handleMessage(Message msg) {
    			// TODO Auto-generated method stub
    			try{
    				mEditText_SHOW.append(mStrMSG);
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    			super.handleMessage(msg);
    		}
        	
        };  
    }
    
}

