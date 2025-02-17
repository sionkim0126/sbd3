package sbd.example.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question {
    //id 속성에 적용한 @Id 애너테이션은 id 속성을 기본키로 지정한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 자동 생성 후 1씩 증가
    private Integer id;

    //제목
    @Column(length = 200)
    private String subject;

    //내용
    @Column( columnDefinition = "TEXT")
    private String content;

    //작성일자(time)
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    //CREATE TABLE question (
    //    id IDENTITY PRIMARY KEY,  -- 자동 증가하는 기본 키
    //    subject VARCHAR(200),  -- 제목 (최대 200자)
    //    content TEXT,  -- 내용 (긴 글 가능)
    //    create_date TIMESTAMP  -- 작성일자
    //);
}
