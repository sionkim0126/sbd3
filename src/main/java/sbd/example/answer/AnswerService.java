package sbd.example.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbd.example.question.Question;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnsewerRepository ansewerRepository;

    public void create(Question question, String content){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.ansewerRepository.save(answer);
    }
}
