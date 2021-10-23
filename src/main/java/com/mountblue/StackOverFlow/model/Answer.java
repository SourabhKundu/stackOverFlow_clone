package com.mountblue.StackOverFlow.model;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Integer answerId;

    @Column(name = "content", columnDefinition = "text" )
    private String content;

    @Column(name = "createdAt")
    private Date createTime;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "answer",
            cascade = CascadeType.ALL)
    List<AnsComment> comments = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id ")
    private User  author;

    @Column(name = "is_accepted")
    private Boolean isAccepted = false;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "answer_upvotes",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> upVotes = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "answer_downvotes",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> downVotes = new HashSet<>();

    public Answer() {
    }

    public Answer(Integer answerId, String content, Date createTime, String userName, String email, List<AnsComment> comments, Question question, User author, Boolean isAccepted, Set<User> upVotes, Set<User> downVotes) {
        this.answerId = answerId;
        this.content = content;
        this.createTime = createTime;
        this.userName = userName;
        this.email = email;
        this.comments = comments;
        this.question = question;
        this.author = author;
        this.isAccepted = isAccepted;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public List<AnsComment> getComments() {
        return comments;
    }

    public void setComments(List<AnsComment> comments) {
        this.comments = comments;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Set<User> getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(Set<User> upVotes) {
        this.upVotes = upVotes;
    }

    public Set<User> getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(Set<User> downVotes) {
        this.downVotes = downVotes;
    }

    public Integer getRating() {
        if (upVotes != null && downVotes != null) {
            return upVotes.size() - downVotes.size();
        }
        return 0;
    }
}
