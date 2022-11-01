package com.wgilooy.blog.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wgilooy.blog.dto.PostDTO;
import com.wgilooy.blog.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    
    @PostMapping("/api/posts")
    public void get(@RequestBody @Valid PostDTO request) throws Exception {
        log.info("post={}", request);
        postService.write(request);
        
        // 데이터 검증 01
        // String title = post.getTitle();
 
        // if(title == null || title.equals("")) {
        //     throw new Exception("제목이 없습니다.");
        // }

        // 데이터 검증 02 validation 활용 @NotBlank, @Valid
        // 여기서 문제 컨트롤로 오기전에 에러를 밷어서 에러를 처리할 수가 없다.
        // 에러 메세지 처리하는 법 BindingResult!
        
        // 근데 에러 처리하는 방식이 힘들다....
        // if(result.hasErrors()) {
        //     List<FieldError> FieldErrors = result.getFieldErrors();
        //     FieldError firstFieldError = FieldErrors.get(0);
        //     String fieldName = firstFieldError.getField(); // title
        //     String errorMessage = firstFieldError.getDefaultMessage(); // 에러 메세지

        //     log.info("fieldName={}, errorMessage={}", fieldName, errorMessage);

        //     Map<String, String> error = new HashMap<>();
        //     error.put(fieldName, errorMessage);
        //     return error;
        // }

        // 데이터 검증 03 ControllerAdvice 모든 controller의 에러처리를 담당한다.
        // 근데 BindingResult이 ControllerAdvice보다 우선적으로 잡혀서 없앤다.


    }

}
