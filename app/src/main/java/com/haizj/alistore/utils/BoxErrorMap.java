/**
 * Copyright (C) 2015 The AndroidPhoneStudent Project
 */
package com.haizj.alistore.utils;

import com.hyena.framework.error.ErrorMap;

import java.util.HashMap;

public class BoxErrorMap implements ErrorMap {

	public static final String ERROR_CODE_CUSTOM_ERROR = "20014";
    public static final String HOMEWORK_UNCOMMIT_ERROR = "20404";
    public static final String PHONE_NUMBER_HAS_REGISTED_ERROR = "20501";
    public static final String PHONE_NUMBER_NOT_EXIST_ERROR = "20505";
    public static final String CAPTCHA_VALIDATE_ERROR = "20506";
    public static final String CAPTCHA_EXPIRED_ERROR = "20507";
    public static final String HOMEWORK_REVOKED_ERROR = "20601";

    public static final String TIP_USER_INFO_ERROR = "用户数据异常，请重新登录!";

	
    private HashMap<String, String> mErrorMap = null;
	
    public BoxErrorMap() {
	}
    
	@Override
	public String getErrorHint(String errorCode, String descript) {
		String hint = descript;
        return hint;
	}


}
