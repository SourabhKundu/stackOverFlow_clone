package com.mountblue.StackOverFlow.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column( name = "content")
    private String content;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;
    @Column(name = "create_date")
    private Date createDate;

  /*  @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable = true)
    private Question question;*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id", nullable = true)
    private Answer answer;

    public Comment() {
    }

    public Comment(Integer commentId, String content, String userName, String email, Date createDate,
                    Answer answer) {
        this.commentId = commentId;
        this.content = content;
        this.userName = userName;
        this.email = email;
        this.createDate = createDate;
       // this.question = question;
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

  /*  public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }*/
}
