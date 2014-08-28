package com.deng.fm.factory;

import com.deng.fm.food.EventLog;
import com.deng.fm.food.Log;

public class EventFactory extends LogFactory {

	public Log create() {
		// TODO Auto-generated method stub
		return new EventLog();
	}

}
