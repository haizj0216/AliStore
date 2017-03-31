/*
 * Copyright (C) 2015 The AndroidPhoneStudent Project
 */
package com.haizj.alistore.utils;

import com.hyena.framework.servcie.BaseServiceManager;

/**
 * 业务相关服务管理器
 * @author yangzc
 */
public class BoxServiceManager extends BaseServiceManager {

	public BoxServiceManager() {
		super();
		//初始化所有服务
		initFrameServices();
		initServices();
	}

	@Override
	public Object getService(String name) {
		return super.getService(name);
	}

	@Override
	public void releaseAll() {
		super.releaseAll();
	}

	/**
	 * 初始化所有服务
	 */
	private void initServices() {
	}

}
