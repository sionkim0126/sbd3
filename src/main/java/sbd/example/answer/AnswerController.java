package sbd.example.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value = "content")
                               String content){
        Question question = this.questionService.getQuestion(id);
        // TODO: 답변을 저장한다. (todoList) 아직 해결되지 않은 문제나 추가로 작업해야 하는 부분을 표시한다
        this.answerService.create(question, content);
        return String.format("redirect:/question/detail/%s", id);
        //흐름에 따라 사용자가 폼을 제출하면 답변이 저장되고, 해당 질문의 상세 페이지로 이동하게 됩니다.
    }

}
