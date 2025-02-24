package sbd.example.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@RequestMapping("/question")
//앞으로 QuestionController.java에서 URL을 매핑할 때 반드시 /question으로 시작한다는 것을 기억해 두자.
@RequiredArgsConstructor // final 필드만을 매개변수로 갖는 생성자를 자동으로 생성해줌
@Controller // 이 클래스가 Spring MVC의 컨트롤러 역할을 한다고 알려줌
public class QuestionController {

    // private final QuestionRepository questionRepository;
    private final QuestionService questionService;  // 의존성 주입 (Service를 통해 비즈니스 로직 처리)

    @GetMapping("/list")
    public String list(Model model) {
        // questionService를 통해 질문 목록 데이터를 가져옴
        List<Question> questionList = this.questionService.getList();

        // Model 객체를 사용해 데이터를 뷰(HTML)로 전달
        model.addAttribute("questionList", questionList);

        return "question_list";
        //컨트롤러 → 서비스 → 리포지터리 순서로 접근하는 과정을 거쳐 데이터를 처리할 것이다.
    }
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
