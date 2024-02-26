package edu.java.service;

import edu.java.clients.StackOverflowClient;
import edu.java.response.StackOverFlowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StackOverFlowService {
    private final StackOverflowClient stackOverflowClient;

    public StackOverFlowResponse getStackOverFlowInfo(String id) {
        return stackOverflowClient.getInfo(id);
    }
}
