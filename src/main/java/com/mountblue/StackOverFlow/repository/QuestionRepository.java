package com.mountblue.StackOverFlow.repository;

import com.mountblue.StackOverFlow.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query("SELECT p FROM Question p WHERE lower(p.title) LIKE %?1%" +
            " OR lower(p.tag) LIKE %?1%")
    Page<Question> findAll2(String keyword, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.title LIKE %?1% OR " +
            "lower(q.tag) LIKE %?1%")
    public List<Question> findAll(String keyword);
}
