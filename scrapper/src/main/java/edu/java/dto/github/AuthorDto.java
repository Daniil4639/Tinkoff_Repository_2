package edu.java.dto.github;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class AuthorDto {

    private String name;
    private String email;
    private OffsetDateTime date;
}
