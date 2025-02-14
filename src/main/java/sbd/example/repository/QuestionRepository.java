package sbd.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbd.example.entitys.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
