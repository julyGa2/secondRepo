package com.ga2board.main.util;

import java.util.UUID;

public class CommonUtils {
	
	//DB를 생성할 때 서버에 저장될 파일명은 32글자
	
	public static String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }



}
