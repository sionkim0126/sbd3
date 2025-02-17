package sbd.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbd.example.entitys.Question;

import java.util.List;

//JpaRepository는 JPA가 제공하는 인터페이스 중 하나로 CRUD 작업을 처리하는 메서드들을 이미 내장하고 있어
// 데이터 관리 작업을 좀 더 편리하게 처리할 수 있다.
//ex) save, findAll , findId , findName등등
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);
}
