package edu.java.response.resource.github;

import edu.java.dto.github.CommitDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GitHubExtendedResponse {

    private List<String> branches = new ArrayList<>();
    private List<List<CommitDto>> commitsByBranches = new ArrayList<>();
}
