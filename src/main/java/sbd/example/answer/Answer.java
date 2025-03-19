package sbd.example.answer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sbd.example.question.Question;
import sbd.example.user.SiteUser;

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

    //수정일자
    private LocalDateTime modifyDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    //CREATE TABLE answer (
    //    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 자동 증가하는 기본 키
    //    content TEXT NOT NULL,                 -- 답변 내용 (긴 글 가능)
    //    create_date DATETIME NOT NULL,         -- 생성 날짜 및 시간
    //
    //    question_id BIGINT,                    -- 외래 키 (Question 테이블 참조)
    //    site_user_id BIGINT,                   -- 외래 키 (SiteUser 테이블 참조)
    //
    //    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE,
    //    FOREIGN KEY (site_user_id) REFERENCES site_user(id) ON DELETE CASCADE
    //);
}
