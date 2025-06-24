package com.dawkastresu.git_proxy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="REPOSITORIES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitRepoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String description;
    private String cloneUrl;
    private Integer watchersCount;
    private String createdAt;

}
