package com.deng.fm.main;

import com.deng.fm.factory.EventFactory;
import com.deng.fm.factory.LogFactory;
import com.deng.fm.food.Log;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LogFactory logFactory = new EventFactory();
		Log log = logFactory.create();
		
		log.write();
	}

}
