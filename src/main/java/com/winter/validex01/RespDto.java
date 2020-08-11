package com.winter.validex01;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RespDto<T> {

	private int statusCode; // 1, 2, 3, 4, 5 ... 나만의 규칙을 인터페이스로 정하는 것이 좋음
	private String msg;
	
	private T data; // 에러랑 데이터 담아줌
}
