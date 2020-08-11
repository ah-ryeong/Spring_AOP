package com.winter.validex01;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data // 원래 Model에는 data 붙이면 X , setter가 있으면 안되기 때문에!
// setter는 생성자, DB에서 들고온 정보를 넣을때만 사용
public class ProjectTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 10, message = "Summary cannot exceed the length")
	@NotBlank(message = "Summary cannot be blank")
	private String summary;
	@NotBlank(message = "AcceptanceCriteria cannot be blank")
	private String acceptanceCriteria;
	private String status;
	@Email(message = "Your email XXX")
	private String email;
}

