package sbd.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbd.example.entitys.Answer;

public interface AnsewerRepository extends JpaRepository<Answer, Integer> {

}
