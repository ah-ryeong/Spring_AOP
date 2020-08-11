package com.winter.validex01;

// 구체적으로 만들면 힘듬, 구분하기 위해서 메세지만 읽으면 됨
public interface StatusCode {
	
	int OK = 1; // 정상
	int FAIL = -1; // 실패 
}
