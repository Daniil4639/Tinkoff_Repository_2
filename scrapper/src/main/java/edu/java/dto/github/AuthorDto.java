package edu.java.dto.github;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@Setter
@Getter
@RequiredArgsConstructor
public class AuthorDto {

    private String name;
    private String email;
    private OffsetDateTime date;
}
