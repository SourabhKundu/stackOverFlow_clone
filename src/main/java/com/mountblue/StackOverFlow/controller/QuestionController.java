package com.mountblue.StackOverFlow.controller;

import com.mountblue.StackOverFlow.model.Question;
import com.mountblue.StackOverFlow.model.Tag;
import com.mountblue.StackOverFlow.model.User;
import com.mountblue.StackOverFlow.service.QuestionService;
import com.mountblue.StackOverFlow.service.UserService;
import com.mountblue.StackOverFlow.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class QuestionController {
    QuestionService questionService;
    UserService userService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String createQuestion(Model model) {
        User user = userService.getUserFromContext();
        model.addAttribute("question", new Question());
        model.addAttribute("user", user);

        return "newquestion";
    }

    @PostMapping("/postQuestion")
    public String saveQuestion(@ModelAttribute("question") Question question) {
        User user = userService.getUserFromContext();
        Set<Tag> quesTags = new HashSet<>();
        String tagString = question.getTag();
        String[] tags = tagString.split(" ");
        for(String tag:tags ){
            if(tag.length() > 0){
                Tag currTag = new Tag();
                currTag.setTagName(tag.trim());
                quesTags.add(currTag);
            }
        }
        question.setTags(quesTags);
        question.setAuthor(user);
        questionService.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/questions")
    public String showQuestions(Model model) {
        User user = userService.getUserFromContext();
        List<Question> listQuestions = questionService.getAllQuestions();

        model.addAttribute("listQuestions", listQuestions);
        model.addAttribute("user", user);
        return "questions";
    }

    @GetMapping("/")
    public String showHome(Model model) {
        User user = userService.getUserFromContext();
        List<Question> listQuestions = questionService.getAllQuestions();
        model.addAttribute("listQuestions", listQuestions);
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/questions/{questionId}")
    public String getSelectedQuestion(@PathVariable("questionId") Integer questionId,
                                      Model model) {
        User user = userService.getUserFromContext();
        Question question = questionService.getQuestionById(questionId);
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        return "question";
    }

    public String showQuestion(Integer questionId, Model model) {
        User user = userService.getUserFromContext();
        Question question = questionService.getQuestionById(questionId);
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        return "question";
    }


    @PostMapping("/question/edit/{questionId}")
    public String editQuestion(@PathVariable(value = "questionId") Integer questionId,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("tag") String tag,
                           Model model) {
        User user = userService.getUserFromContext();
        Question question = questionService.getQuestionById(questionId);

        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);

        questionService.save(question);
        return getSelectedQuestion(questionId, model);
    }

    @GetMapping("/update/{questionId}")
    public ModelAndView updatePost(@PathVariable(name = "questionId") Integer questionId){
        ModelAndView editView = new ModelAndView("editquestion");
        if(questionId != null) {
            Question question = questionService.getQuestionById(questionId);
            question.setQuestionId(questionId);
            editView.addObject("question", question);
        }
        return editView;
    }

    @GetMapping("/question/delete/{questionId}")
    public String deleteQuestion(@PathVariable(value = "questionId") Integer questionId) {
        questionService.deletePostById(questionId);
        return "redirect:/questions";
    }

    @GetMapping("/questionUpvote/{questionId}")
    public String upvoteQuestion(@PathVariable(name = "questionId") Integer questionId, Model model){
        User user = userService.getUserFromContext();
        Question question = questionService.getQuestionById(questionId);
        Set<User>upVotes= question.getUpVotes();
        Set<User>downVotes=question.getDownVotes();

        if(upVotes.contains(user)){
             upVotes.remove(user);
        }else{
            if(downVotes.contains(user)){
                downVotes.remove(user);
            }
            upVotes.add(user);
        }
        question.setUpVotes(upVotes);
        question.setDownVotes(downVotes);
        questionService.save(question);
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        return "redirect:/questions/{questionId}";
    }

    @GetMapping("/questionDownvote/{questionId}")
    public String downVoteQuestion(@PathVariable(name = "questionId") Integer questionId, Model model){
        User user = userService.getUserFromContext();
        Question question = questionService.getQuestionById(questionId);
        Set<User>upVotes= question.getUpVotes();
        Set<User>downVotes=question.getDownVotes();

        if(downVotes.contains(user)){
             downVotes.remove(user);
        }else {
            if(upVotes.contains(user)){
                upVotes.remove(user);
            }
            downVotes.add(user);
        }
        question.setUpVotes(upVotes);
        question.setDownVotes(downVotes);
        questionService.save(question);
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        return "redirect:/questions/{questionId}";
    }
}