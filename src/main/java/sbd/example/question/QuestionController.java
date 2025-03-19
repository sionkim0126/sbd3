package sbd.example.question;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import sbd.example.answer.AnswerForm;
import sbd.example.user.SiteUser;
import sbd.example.user.UserService;

import java.security.Principal;


@RequestMapping("/question")
//앞으로 QuestionController.java에서 URL을 매핑할 때 반드시 /question으로 시작한다는 것을 기억해 두자.
@RequiredArgsConstructor // final 필드만을 매개변수로 갖는 생성자를 자동으로 생성해줌
@Controller // 이 클래스가 Spring MVC의 컨트롤러 역할을 한다고 알려줌
public class QuestionController {

    // private final QuestionRepository questionRepository;
    private final QuestionService questionService; // 의존성 주입 (Service를 통해 비즈니스 로직 처리)
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        // questionService를 통해 질문 목록 데이터를 가져옴
        /*List<Question> questionList = this.questionService.getList();*/

        // Model 객체를 사용해 데이터를 뷰(HTML)로 전달
        /*model.addAttribute("questionList", questionList);*/

        /*위는 페이징기능 없이 조회 후 전달하는 기능 아래는 페이징 기능 추가 하여 조회*/
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
        //컨트롤러 → 서비스 → 리포지터리 순서로 접근하는 과정을 거쳐 데이터를 처리할 것이다.
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        // 빈 폼 객체를 템플릿에 전달
        return "question_form";  // question_form.html을 보여줌
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(/*데이터 유효성 검사를 하기 전 그냥 진행했던 단계
                                 @RequestParam(value = "subject") String subject,
                                 @RequestParam(value = "content") String content*/
            @Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        /*데이터 유효성 검사를 하기 전 그냥 진행했던 단계
        this.questionService.create(subject, content);*/
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";

        /*GET 요청 시: 빈 QuestionForm 객체가 폼에 전달되어야,
     입력 필드에 바인딩이 가능하고, 폼을 제대로 렌더링할 수 있어요.

    POST 요청 시: 사용자가 입력한 데이터를 서버로 보내면, 유효성 검사를 진행하고,
     유효성 오류가 있으면 다시 폼을 띄우고, 오류 메시지를 표시해줘요.*/
    }
    @PreAuthorize("isAuthenticated()") //백엔드 보안 강화를 위해 추가
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id,
                                 Principal principal){
        Question question = this.questionService.getQuestion(id);
        //principal.getName()을 호출하면 현재 로그인한 사용자의 사용자명(사용자ID)을 알 수 있다. *기억이 안나 다시한번 적어둔다.
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }
}
