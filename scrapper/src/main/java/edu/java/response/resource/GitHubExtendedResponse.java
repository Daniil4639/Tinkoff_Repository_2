package edu.java.response.resource;

import edu.java.dto.github.CommitDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

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
