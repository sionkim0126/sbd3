package sbd.example.answer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sbd.example.question.Question;
import sbd.example.user.SiteUser;

import java.time.LocalDateTime;
import java.util.Set;

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

    @ManyToMany
    Set<SiteUser> voter;

    //CREATE TABLE answer (
    //    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 자동 증가하는 기본 키
    //    content TEXT NOT NULL,                 -- 답변 내용 (긴 글 가능)
    //    create_date DATETIME NOT NULL,         -- 생성 날짜 및 시간
    //    modify_date DATETIME,                  -- 수정 날짜 및 시간
    //
    //    question_id BIGINT,                    -- 외래 키 (Question 테이블 참조)
    //    site_user_id BIGINT,                   -- 외래 키 (SiteUser 테이블 참조)
    //
    //    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE, -- Question과의 관계
    //    FOREIGN KEY (site_user_id) REFERENCES site_user(id) ON DELETE CASCADE -- SiteUser와의 관계
    //);

    // @ManyToMany 애너테이션을 사용해 다대다 관계로 속성을 생성하면 새로운 테이블을 만들어 관련 데이터를 관리한다.
    // 여기서 생성된 테이블의 인덱스 항목을 펼쳐 보면 서로 연관된 엔티티의 고유 번호(즉, ID)가 기본키로 설정되어 다대다 관계임을 알 수 있다.
//CREATE TABLE answer_voter (
    //    answer_id BIGINT NOT NULL,             -- Answer 테이블의 외래 키 (FK)
    //    voter_id BIGINT NOT NULL,              -- SiteUser 테이블의 외래 키 (FK)
    //    PRIMARY KEY (answer_id, voter_id),     -- 복합 기본 키로 중복 방지
    //    FOREIGN KEY (answer_id) REFERENCES answer(id) ON DELETE CASCADE,     -- FK 설정
    //    FOREIGN KEY (voter_id) REFERENCES site_user(id) ON DELETE CASCADE    -- FK 설정
    //);
}
