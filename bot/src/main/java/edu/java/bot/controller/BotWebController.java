package edu.java.bot.controller;

import edu.java.bot.service.MessageService;
import edu.java.exceptions.IncorrectRequest;
import edu.java.requests.LinkUpdateRequest;
import edu.java.responses.ApiErrorResponse;
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
    @ApiResponse(responseCode = "200", description = "Обновление обработано")
    @ApiResponse(
        responseCode = "400", description = "Некорректные параметры запроса",
        content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
    )
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String sendUpdates(@RequestBody LinkUpdateRequest request) throws IncorrectRequest {
        if (request.getUrl() == null || request.getTgChatIds() == null) {
            throw new IncorrectRequest("Некорректные параметры запроса");
        }

        service.sendUpdate(request);
        return "Обновление обработано";
    }
}
