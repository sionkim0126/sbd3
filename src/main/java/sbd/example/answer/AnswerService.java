package sbd.example.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbd.example.DataNotFoundException;
import sbd.example.question.Question;
import sbd.example.user.SiteUser;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnsewerRepository ansewerRepository;

    public void create(Question question, String content, SiteUser author){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.ansewerRepository.save(answer);
    }
    public Answer getAnswer(Integer id){
        Optional<Answer> answer = this.ansewerRepository.findById(id);
        if(answer.isPresent()){
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer , String content){
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.ansewerRepository.save(answer);
    }

    public void delete(Answer answer){
        this.ansewerRepository.delete(answer);
    }
}
