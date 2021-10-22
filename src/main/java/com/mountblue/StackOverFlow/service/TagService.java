package com.mountblue.StackOverFlow.service;

import com.mountblue.StackOverFlow.model.Tag;
import com.mountblue.StackOverFlow.model.User;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags();

    List<Tag> findTagByName(String keyword);
}
