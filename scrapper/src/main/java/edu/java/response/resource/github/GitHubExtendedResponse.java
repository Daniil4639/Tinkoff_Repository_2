package edu.java.response.resource.github;

import edu.java.dto.github.CommitDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubExtendedResponse {

    private List<String> branches;
    private List<List<CommitDto>> commitsByBranches;

    public GitHubExtendedResponse() {
        branches = new ArrayList<>();
        commitsByBranches = new ArrayList<>();
    }
}
