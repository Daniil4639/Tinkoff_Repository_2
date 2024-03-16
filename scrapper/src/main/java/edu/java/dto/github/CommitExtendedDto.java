package edu.java.dto.github;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CommitExtendedDto {

    private String sha;
    private CommitDto commit;
}
