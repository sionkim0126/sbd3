package sbd.example.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sbd.example.DataNotFoundException;
import sbd.example.user.SiteUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList(){
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        }else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content , SiteUser user){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    public Page<Question> getList(int page){
        /*현재 질문 목록은 등록한 순서대로 데이터가 표시된다.
         하지만 대부분의 게시판 서비스는 최근에 작성한 게시물이 가장 위에 보이는 것이 일반적이다.
          이를 구현하기 위해 Question Service를 다음과 같이 수정해 보자.*/
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable  = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);

    //위 방법은 여러 조건을 추가로 달기 용의하다 아래방법은 역순으로 조회 만 설정한 코딩방법이다
    //위는 ArrayList로 하여 List에 역순 설정을 적용한 방법이다 추가 설정도 가능하다
    /*public Page<Question> getListEx(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createDate"));
        return this.questionRepository.findAll(pageable);
    }*/
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }
}
