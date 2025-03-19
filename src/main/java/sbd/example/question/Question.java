package sbd.example.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sbd.example.answer.Answer;
import sbd.example.user.SiteUser;

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

    //수정일자(time)
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    //@OneToMany: "하나의 엔티티가 여러 개의 엔티티를 가질 수 있는 관계"
    private List<Answer> answerList;

    @ManyToOne
    //@ManyToOne: "여러 개의 엔티티가 하나의 엔티티와 연결되는 관계"
    private SiteUser author;

    //@OneToMany와 @ManyToOne은 반대 개념이며, 데이터베이스에서는 외래 키(Foreign Key)로 표현됩니다.
    //즉 question 테이블의 site_user_id 필드는 site_user의 id를 참조.

    //CREATE TABLE question (
    //    id BIGINT AUTO_INCREMENT PRIMARY KEY,    -- 기본 키 (PK), 자동 증가
    //    subject VARCHAR(200) NOT NULL,          -- 제목 (최대 200자)
    //    content TEXT NOT NULL,                  -- 내용 (TEXT 타입)
    //    create_date DATETIME NOT NULL,          -- 생성일 (LocalDateTime)
    //
    //    site_user_id BIGINT,                     -- 외래 키 (FK) (SiteUser 참조)
    //    FOREIGN KEY (site_user_id) REFERENCES site_user(id) ON DELETE CASCADE
    //);
}
