package com.deng.fm.food;

/**
 * @author Administrator
 *  为什么要定义工厂模式：
 *  假设两个工厂生产两种相同的名字的产品，但产品内容并不相同。
 *  
 *  servlet类所在包命名规范：公司名称.开发组名称.项目名称.web.servlet
 *	例如：net.linkcn.web.servlet
 */
public abstract class Log {
	public abstract void write();
}
