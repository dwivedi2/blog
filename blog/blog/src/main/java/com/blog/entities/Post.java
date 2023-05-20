package com.blog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data  //if i am using @data lombok annotateson seter and geter will be auto maticly create
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title" , nullable = false)
    private String title;
    @Column(name = "description" , nullable = false)
    private String description;
    @Column(name = "content" , nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

}
