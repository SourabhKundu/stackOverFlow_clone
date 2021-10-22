package com.mountblue.StackOverFlow.service.impl;

import com.mountblue.StackOverFlow.model.Tag;
import com.mountblue.StackOverFlow.model.User;
import com.mountblue.StackOverFlow.repository.TagRepository;
import com.mountblue.StackOverFlow.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> findTagByName(String keyword) {
        return tagRepository.findAll(keyword);
    }
}
