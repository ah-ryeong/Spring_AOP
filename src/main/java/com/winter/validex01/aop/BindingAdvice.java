package com.winter.validex01.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.winter.validex01.RespDto;
import com.winter.validex01.StatusCode;

// 공통관심사 : advice
@Component
@Aspect // AOP 등록 -> 메모리에 떠야함(@Component) -> 등록완료
public class BindingAdvice {
	
	@Before("execution(* com.winter.validex01.test.BindControllerTest.*(..))")
	public void test1() {
		System.out.println("BindCntroller에 오신 것을 환영합니다.");
	}
	
	@After("execution(* com.winter.validex01.test.BindControllerTest.*(..))")
	public void test2() {
		System.out.println("BindCntrollerTest를 이용해 주셔서 감사합니다..");
	}

	// @Before : 핵심로직의 직전에 실행, @After : 핵심로직 뒤에 실행, @Around : 핵심로직 앞, 뒤로 실행
	// 뒤에 처리하려면 해당 로직이 언제 끝나는지 알아야함 (끝나고 처리하는건 request 받아서 뭔가 변형하고 싶다는 것임)
	// 함수 끝나기 전에 처리 : 필터링 , 함수 끝나고 처리 : request에 접근(다시 응답해주기 위해)
	// 앞 뒤로 접근 : 들어가기 전에 request 받아서 처리하고 끝나고 request 처리
//	@Around("execution(* com.winter.validex01..*Controller.*(..))") // 내 Package의 모든 Controller에 동작하라는 뜻
	public Object validationHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		// JoinPoint를 적어두면 받는다 -> 핵심로직을 받는 것
		
		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String method = proceedingJoinPoint.getSignature().getName();
		
		System.out.println("type : " + type);
		System.out.println("method : " + method);
		
		Object[] args = proceedingJoinPoint.getArgs(); // getArgs : JoinPoint의 파라메터
		
		for (Object arg: args) {
			if (arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>(); // 오류등의 메세지만 보기 위해 만들어줌
					
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					
					RespDto<?> respDto = RespDto.builder()
							.statusCode(StatusCode.FAIL)
							.msg(method +"요청에 실패했습니다.")
							.data(errorMap)
							.build();
					
					return new ResponseEntity<RespDto>(respDto, HttpStatus.BAD_REQUEST);
				}
			}
		}
		
		return proceedingJoinPoint.proceed();
	}
}
