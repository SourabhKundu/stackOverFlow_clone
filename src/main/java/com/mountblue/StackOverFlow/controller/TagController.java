package com.mountblue.StackOverFlow.controller;

import com.mountblue.StackOverFlow.model.Tag;
import com.mountblue.StackOverFlow.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String findAllTags(Model model){
        List<Tag> allTags = tagService.getAllTags();

        model.addAttribute("tags", allTags);
        return "tags";
    }
}