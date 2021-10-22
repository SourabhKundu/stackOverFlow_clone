package com.mountblue.StackOverFlow.repository;

import com.mountblue.StackOverFlow.model.Tag;
import com.mountblue.StackOverFlow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("SELECT q FROM Tag q WHERE q.tagName LIKE %?1% ")
    List<Tag> findAll(String keyword);
}
