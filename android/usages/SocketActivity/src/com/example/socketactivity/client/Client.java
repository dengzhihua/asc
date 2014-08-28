package com.example.socketactivity.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	private static final int PORT = 54321;
	private static ExecutorService exec = Executors.newCachedThreadPool();

	public Client(){
		try{
			Socket socket = new Socket("222.18.14.9",PORT);
			exec.execute(new Sender(socket));
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String msg;
			while((msg=br.readLine())!=null){
				System.out.println(msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	class Sender implements Runnable {
		private Socket socket;
		
		public Sender (Socket socket){
			this.socket = socket;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				BufferedReader br = new BufferedReader(
						new InputStreamReader(System.in));
			
				PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
				String msg ;
				
				while(true){
					msg = br.readLine();
					pw.println(msg);
					
					if(msg.trim().equals("exit")){
						pw.close();
						br.close();
						exec.shutdown();
						break;
					}
				}
			}catch (Exception e){
				
			}
		}
	}
}
