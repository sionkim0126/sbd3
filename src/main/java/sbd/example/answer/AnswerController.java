package sbd.example.answer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sbd.example.DataNotFoundException;
import sbd.example.question.Question;
import sbd.example.question.QuestionService;
import sbd.example.user.SiteUser;
import sbd.example.user.UserService;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    /*@PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value = "content")
                               String content){
        Question question = this.questionService.getQuestion(id);
        //(todoList) 아직 해결되지 않은 문제나 추가로 작업해야 하는 부분을 표시한다
        this.answerService.create(question, content);
        return String.format("redirect:/question/detail/%s", id);
        //흐름에 따라 사용자가 폼을 제출하면 답변이 저장되고, 해당 질문의 상세 페이지로 이동하게 됩니다.
    }*/
    //유효성 검사 추가
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id ,
                               @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal){
        Question question = this.questionService.getQuestion(id);
        //principal.getName()을 호출하면 현재 로그인한 사용자의 사용자명(사용자ID)을 알 수 있다.
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail";
            // 난 여기서 궁금증이 생겼다 분명 return "question_detail";인데
            // 왜 URL은 http://localhost:8080/question/create/35 인가 하는 궁금증이 생겼다
            // 결론은 return "question_detail" 은 forward방식으로 새로운 요청이 아닌 렌더링하여 표시하는 것이다.
            // 만약 URL을 유지하려면 return String.format("redirect:/question/detail/%s", id);를 사용하면 된다.
            // 대신 redirect방식을 사용하려면 사용자가 입력한 값을 유지하는 추가 작업이 필요함.
        }

        this.answerService.create(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s",id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id , Principal principal){
        Answer answer = this.answerService.getAnswer(id);
        if( !answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id){
        Answer answer = this.answerService.getAnswer(id);
        if(!answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
}
