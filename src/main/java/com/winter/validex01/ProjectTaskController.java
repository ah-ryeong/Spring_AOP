package com.winter.validex01;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/board")
public class ProjectTaskController {

	@Autowired // @Autowired로 주입받으려면 클래스 자체(ProjectTaskController)도 IoC되어야 한다.
	private ProjectTaskRepository projectTaskRepository;
	
	@PostMapping({"", "/"})
	public ResponseEntity<?> save(@Valid @RequestBody ProjectTask requestProjectTask, BindingResult bindingResult) { // ﻿validation 오류가 나면 BindingResult가 채감
		
		// 받은 값 바로 save 시키기
		ProjectTask entityProjectTask = projectTaskRepository.save(requestProjectTask);
		// entityProjectTask : DB에 있는 값 , requestProjectTask : DB에 없는값
		
		// 정상
		RespDto<?> respDto = RespDto.builder()
				.statusCode(StatusCode.OK)
				.msg("save 요청에 성공했습니다.")
				.data(entityProjectTask)
				.build();
		
		return new ResponseEntity<RespDto>(respDto, HttpStatus.CREATED); // 1 : 정상, 2 : 아이디 중복, 2 : ... 이런식으로 만들어가면 됨, 이게 번거로우면 http 상태코드 사용해도 됨
	}
}
