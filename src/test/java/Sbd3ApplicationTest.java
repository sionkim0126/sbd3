import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sbd.example.Sbd3Application;
import sbd.example.answer.Answer;
import sbd.example.question.Question;
import sbd.example.answer.AnsewerRepository;
import sbd.example.question.QuestionRepository;
import sbd.example.question.QuestionService;
import sbd.example.user.SiteUser;
import sbd.example.user.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Sbd3Application.class)
public class Sbd3ApplicationTest {

    @Autowired //의존성 주입으로 하여 questionrepository의 객체 주입
    public QuestionRepository questionRepository;

    @Autowired
    public AnsewerRepository ansewerRepository;

    @Autowired
    public QuestionService questionService;

    @Autowired
    public UserService userService;

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
        //결론 : finAll은 전체 객체를 가져옴 ex)'select * from question 의 실행 결과값 2' 이후 속성값이 예상과 같은지 비교한 테스트이고,
        // findById는 'select id, content, createDate, subject from question where id = 1 ' 그 값이 있다면 꺼내어 결과 비교
        }
    }
    @Test
    void findBySubjectTest(){
        Question q = this.questionRepository.findBySubject("Spring Booot 가 무엇인가요?");
        assertEquals(1, q.getId());
        //select id, content, createDate ,subject from question where subject = ?
        //여기까지는 questionRepository에서 하는 일이고 우리는 여기서 ?에 'Spring Boot가 무엇인가요?'라는 값을 넣고 아이디값을 비교
    }

    @Test
    void findBySubjectAndContentTest(){
        Question q = this.questionRepository.findBySubjectAndContent(
                "Spring Booot 가 무엇인가요?", "sbb에 대해 알고 싶습니다!");
        assertEquals(1, q.getId());
        //select id, content, createDate , subject from question where subject = ? and content = ?

    }

    @Test
    void findBySubjectLikeTest(){
        //여러개의 결과가 반환 될 가능성이 있는 경우 List사용!!
        List<Question> questionList = this.questionRepository.findBySubjectLike("Spring%");
        //SELECT * FROM QUESTION WHERE SUBJECT LIKE 'Spring%';
        Question q = questionList.get(0);
        //나온 List중 첫번째를 선택
        assertEquals("Spring Booot 가 무엇인가요?", q.getSubject());
    }

    @Test
    void setSubjectTest(){
        Optional<Question> optionalQuestion = this.questionRepository.findById(1);
        assertTrue(optionalQuestion.isPresent());
        //assertTrue()는 괄호 안의 값이 true(참) 인지를 테스트한다.
        //oq.isPresent()가 false를 리턴하면 오류가 발생하고 테스트가 종료된다.
        Question q = optionalQuestion.get();
        q.setSubject("제목 수정1");
        this.questionRepository.save(q);
    }

    @Test
    void deleteQuestionTest(){
        assertEquals(2, this.questionRepository.count());
        Optional<Question>optionalQuestion = this.questionRepository.findById(1);
        assertTrue(optionalQuestion.isPresent());
        Question q = optionalQuestion.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

    @Test
    void answerSaveTest(){
        //questionRepository의 findById 메서드를 통해 id가 2인 질문 데이터를 가져와
        //답변의 question 속성에 대입해 답변 데이터를 생성했다. 테스트를 수행하면 오류 없이 답변 데이터가 잘 생성될 것이다.
        Optional<Question>op = this.questionRepository.findById(2);
        assertTrue(op.isPresent());
        Question q = op.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        this.ansewerRepository.save(a);
        //INSERT INTO answer (content, create_date, question_id)
        //VALUES ('네 자동으로 생성됩니다.', CURRENT_TIMESTAMP, 2);
    }

    @Test
    void a_findByIdTest(){
        //1.optionalAnswer.get() → Answer 객체를 가져옴
        Optional<Answer>optionalAnswer = this.ansewerRepository.findById(1);
        assertTrue(optionalAnswer.isPresent());
        Answer a = optionalAnswer.get();
        //2.a.getQuestion() → Answer 객체와 연결된 Question 객체를 가져옴
        assertEquals(2, a.getQuestion().getId());
        //3.a.getQuestion().getId() → Question 객체의 ID를 가져옴
    }

    @Test
    @Transactional //메서드가 종료될 때까지 DB 세션이 유지된다.
    void findAnswerByQuestionTest(){
        Optional<Question>op = this.questionRepository.findById(2);
        assertTrue(op.isPresent());
        Question q = op.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
        //SELECT a.*
        //FROM answer a
        //JOIN question q ON a.question_id = q.id
        //WHERE q.id = 2;
    }
    @Test
    void testdata(){
        for (int i= 1; i <= 300; i++){
            String subject = String.format("테스트 데이터 입니다 :[%03d]", i);
            String content = "내용 없음";
            SiteUser siteUser = userService.getUser("test1");
            this.questionService.create(subject, content, siteUser);
        }
    }
}
