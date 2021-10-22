package com.mountblue.StackOverFlow.controller;

import com.mountblue.StackOverFlow.model.Tag;
import com.mountblue.StackOverFlow.model.User;
import com.mountblue.StackOverFlow.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagController {

    @Autowired
    TagService tagService;

    @RequestMapping("/searchTag")
    public String getSearchResult(@RequestParam("search") String keyword, Model model) {
        List<Tag> listTags = tagService.findTagByName(keyword);
        model.addAttribute("tags", listTags);

        return "tags";
    }

    @GetMapping("/tags")
    public String findAllTags(Model model){
        List<Tag> allTags = tagService.getAllTags();

        model.addAttribute("tags", allTags);
        return "tags";
    }
}