package edu.java.retry_constructs;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RetryInfo(@NotNull RetryType type,
                        @NotNull Integer attempts,
                        @NotNull Integer interval,
                        @NotNull List<Integer> codes) {}
