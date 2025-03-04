package sbd.example.answer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sbd.example.question.Question;
import sbd.example.question.QuestionService;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    /*@PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value = "content")
                               String content){
        Question question = this.questionService.getQuestion(id);
        //(todoList) 아직 해결되지 않은 문제나 추가로 작업해야 하는 부분을 표시한다
        this.answerService.create(question, content);
        return String.format("redirect:/question/detail/%s", id);
        //흐름에 따라 사용자가 폼을 제출하면 답변이 저장되고, 해당 질문의 상세 페이지로 이동하게 됩니다.
    }*/

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id ,
                               @Valid AnswerForm answerForm, BindingResult bindingResult){
        Question question = this.questionService.getQuestion(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail";
            // 난 여기서 궁금증이 생겼다 분명 return "question_detail";인데
            // 왜 URL은 http://localhost:8080/question/create/35 인가 하는 궁금증이 생겼다
            // 결론은 return "question_detail" 은 forward방식으로 새로운 요청이 아닌 렌더링하여 표시하는 것이다.
            // 만약 URL을 유지하려면 return String.format("redirect:/question/detail/%s", id);를 사용하면 된다.
            // 대신 redirect방식을 사용하려면 사용자가 입력한 값을 유지하는 추가 작업이 필요함.
        }
        this.answerService.create(question, answerForm.getContent());
        return String.format("redirect:/question/detail/%s",id);
    }
}
