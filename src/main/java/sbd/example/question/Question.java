package sbd.example.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sbd.example.answer.Answer;
import sbd.example.user.SiteUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    //하나의 질문에 여러 사람이 추천할 수 있고 한 사람이 여러 개의 질문을 추천할 수 있다.
    //따라서 @ManyToMany 애너테이션을 사용해야 한다.
    //Set 자료형으로 작성한 이유는 voter 속성값이 서로 중복되지 않도록 하기 위해서이다
    //List 자료형과 달리 여기서는 Set 자료형이 voter 속성을 관리하는데 효율적이다.
    @ManyToMany
    Set<SiteUser> voter;

    //@OneToMany와 @ManyToOne은 반대 개념이며, 데이터베이스에서는 외래 키(Foreign Key)로 표현됩니다.
    //즉 question 테이블의 site_user_id 필드는 site_user의 id를 참조.

//CREATE TABLE question (
    //    id BIGINT AUTO_INCREMENT PRIMARY KEY,    -- 기본 키 (PK), 자동 증가
    //    subject VARCHAR(200) NOT NULL,          -- 제목 (최대 200자)
    //    content TEXT NOT NULL,                  -- 내용 (TEXT 타입)
    //    create_date DATETIME NOT NULL,          -- 생성일 (LocalDateTime)
    //    modify_date DATETIME,                   -- 수정일 (LocalDateTime)
    //
    //    site_user_id BIGINT,                    -- 외래 키 (FK) (SiteUser 참조)
    //    FOREIGN KEY (site_user_id) REFERENCES site_user(id) ON DELETE CASCADE
    //);

    // @ManyToMany 애너테이션을 사용해 다대다 관계로 속성을 생성하면 새로운 테이블을 만들어 관련 데이터를 관리한다.
    // 여기서 생성된 테이블의 인덱스 항목을 펼쳐 보면 서로 연관된 엔티티의 고유 번호(즉, ID)가 기본키로 설정되어 다대다 관계임을 알 수 있다.
//CREATE TABLE question_voter (
    //    question_id BIGINT NOT NULL,             -- Question 테이블의 외래 키 (FK)
    //    voter_id BIGINT NOT NULL,                -- SiteUser 테이블의 외래 키 (FK)
    //    PRIMARY KEY (question_id, voter_id),     -- 복합 기본 키로 중복 방지
    //    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE, -- FK 설정
    //    FOREIGN KEY (voter_id) REFERENCES site_user(id) ON DELETE CASCADE    -- FK 설정
    //);
}
