package edu.java.dto.github;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CommitDto {

    private AuthorDto author;
    private String message;
    private String sha;
}
