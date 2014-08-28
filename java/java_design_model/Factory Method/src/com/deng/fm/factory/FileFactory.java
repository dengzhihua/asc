package com.deng.fm.factory;

import com.deng.fm.food.FileLog;
import com.deng.fm.food.Log;

public class FileFactory extends LogFactory {

	public Log create() {
		// TODO Auto-generated method stub
		return new FileLog();
	}

}
