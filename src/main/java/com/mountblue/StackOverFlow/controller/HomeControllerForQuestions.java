package com.mountblue.StackOverFlow.controller;

import com.mountblue.StackOverFlow.model.Question;
import com.mountblue.StackOverFlow.model.User;
import com.mountblue.StackOverFlow.service.QuestionService;
import com.mountblue.StackOverFlow.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeControllerForQuestions {
    @Autowired
    QuestionService questionService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping("/search2")
    public String searchResult(@RequestParam("searchBar") String keyword, Model model) {
        return showHomePage(keyword, model);
    }

    @RequestMapping("/")
    public String showHomePage(String keyword, Model model) {
        return showPaginationQuestions(1, "createDate", "desc", keyword, model);
    }


    @GetMapping("/page/{pageNo}")
    public String showPaginationQuestions(@PathVariable("pageNo") int pageNo,
                                          @RequestParam(value = "sortField") String sortField,
                                          @RequestParam(value = "sortDir") String sortDir,
                                          String keyword,
                                          Model model) {
        int pageSize = 2;
        Page<Question> page = questionService.findPaginated(pageNo, pageSize, sortField, sortDir, keyword);
        User user = userServiceImpl.getCurrentUser();
        List<Question> listQuestions = page.getContent();

        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        if (model.getAttribute("listQuestions") == null) {
            model.addAttribute("listQuestions", listQuestions);
        }

        return "home";
    }
}
