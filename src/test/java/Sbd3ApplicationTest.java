import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sbd.example.Sbd3Application;
import sbd.example.entitys.Question;
import sbd.example.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Sbd3Application.class)
public class Sbd3ApplicationTest {

    @Autowired //의존성 주입으로 하여 questionrepository의 객체 주입
    public QuestionRepository questionRepository;

    @Test
    void testJpa(){
        Question q1 = new Question();
        q1.setSubject("Spring Booot 가 무엇인가요?");
        q1.setContent("sbb에 대해 알고 싶습니다!");
        q1.setCreateDate(LocalDateTime.now()); //현재시간을 바로 저장하는 명령어
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링 모델 질문입니다");
        q2.setContent("id는 자동생성인가요?");
        q2.setCreateDate(LocalDateTime.now()); //현재시간을 바로 저장하는 명령어
        this.questionRepository.save(q2);

    }

    @Test
    void findAllTest(){
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size()); //객체 전체를 가져오는 것이기 때문에 질문 2개를 가져와서 사이즈가 2이다.

        Question q = all.get(0); //가져온 객체 2개중 0번째인 첫질문이 곧 q로 설정
        assertEquals("Spring Booot 가 무엇인가요?", q.getSubject()); // 설정한 q의 제목을 가져와 비교
    }

    @Test
    void findByIdTest(){
        //optional은 값이 존재할 수도 있고, null일 수도 있는 경우 사용함
        Optional<Question> op = this.questionRepository.findById(1);
        //isPresent()은 결과 값이 있는지 확인 true 는 값이 존재하고 false 라면 존재하지 않음
        if(op.isPresent()){
            Question q = op.get(); //값이 있다면 안에 있는 결과를 꺼냄
            assertEquals("Spring Booot 가 무엇인가요?", q.getSubject());
        }
    }
}
//결론 : finAll은 전체 객체를 가져옴 ex)'select * from question 의 실행 결과값 2' 이후 속성값이 예상과 같은지 비교한 테스트이고,
// findById는 'select id = 1 from question' 그 값이 있다면 꺼내어 결과 비교