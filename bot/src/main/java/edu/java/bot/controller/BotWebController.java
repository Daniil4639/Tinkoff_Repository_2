package edu.java.bot.controller;

import edu.java.bot.api_exceptions.IncorrectUpdateRequest;
import edu.java.bot.requests.LinkUpdateRequest;
import edu.java.bot.responses.ApiErrorResponse;
import edu.java.bot.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class BotWebController {

    private final MessageService service;

    @Operation(summary = "Отправить обновление")
    @ApiResponse(responseCode = "200",
                 content = {@Content(mediaType = "application/json",
                                     schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400",
                 content = {@Content(mediaType = "application/json",
                                     schema = @Schema(implementation = ApiErrorResponse.class))})
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String sendUpdates(@RequestBody LinkUpdateRequest request) throws IncorrectUpdateRequest {
        if (request.getUrl() == null || request.getTgChatIds() == null) {
            throw new IncorrectUpdateRequest("Некорректные параметры запроса");
        }

        service.sendUpdate(request);

        return "Обновление обработано";
    }
}
