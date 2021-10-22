package com.mountblue.StackOverFlow.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "view_counts")
    private int viewCount;

    @Column(name = "vote_counts")
    private int voteCount = 0;

    @Column(name = "reputation")
    private int reputation = 1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createDate;

    @Column(name = "que_tag")
    private String tag;

    @Column(name = "updated_at")
    private Date updateDate = null;

    @OneToMany(mappedBy = "question",
            cascade = {CascadeType.REMOVE})
    List<QuesComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "question",
            cascade = CascadeType.ALL)
    List<Answer> answers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "question_tags",
            joinColumns = {@JoinColumn(name = "question_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id ")
    private User author;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "question_upvotes",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> upVotes = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "question_downvotes",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> downVotes = new HashSet<>();


    public Question() {
    }

    public Question(Integer questionId, String title, String description, int viewCount, int voteCount, int reputation, Date createDate, String tag, Date updateDate, List<QuesComment> comments, List<Answer> answers, Set<Tag> tags, User author, Set<User> upVotes, Set<User> downVotes) {
        this.questionId = questionId;
        this.title = title;
        this.description = description;
        this.viewCount = viewCount;
        this.voteCount = voteCount;
        this.reputation = reputation;
        this.createDate = createDate;
        this.tag = tag;
        this.updateDate = updateDate;
        this.comments = comments;
        this.answers = answers;
        this.tags = tags;
        this.author = author;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<QuesComment> getComments() {
        return comments;
    }

    public void setComments(List<QuesComment> comments) {
        this.comments = comments;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
}
