package sbd.example.answer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sbd.example.question.Question;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column( columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    //CREATE TABLE answer (
    //    id IDENTITY PRIMARY KEY,  -- 자동 증가하는 기본 키
    //    content TEXT,  -- 답변 내용 (긴 글 가능)
    //    create_date TIMESTAMP,  -- 생성 날짜 및 시간
    //    question_id INTEGER,  -- 외래 키 (Question 테이블의 ID를 참조)
    //    CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
    //);
}
