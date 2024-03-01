package edu.java.controller;

import edu.java.api_exceptions.DoesNotExistException;
import edu.java.api_exceptions.IncorrectChatOperationRequest;
import edu.java.requests.api.AddLinkRequest;
import edu.java.requests.api.RemoveLinkRequest;
import edu.java.response.api.ApiErrorResponse;
import edu.java.response.api.LinkResponse;
import edu.java.response.api.ListLinksResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
public class ScrapperLinksController {

    private final static String INCORRECT_REQUEST_PARAMS = "Некорректные параметры запроса";

    @Operation(summary = "Получить все отслеживаемые ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылки успешно получены",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = String.class))}),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = ApiErrorResponse.class))})
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListLinksResponse getLinks(@RequestParam Integer tgChatId) throws
        IncorrectChatOperationRequest {

        if (tgChatId == null) {
            throw new IncorrectChatOperationRequest(INCORRECT_REQUEST_PARAMS);
        }

        return new ListLinksResponse(null, 0);
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = String.class))}),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = ApiErrorResponse.class))})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LinkResponse trackLink(@RequestParam Integer tgChatId,
        @RequestBody AddLinkRequest request) throws IncorrectChatOperationRequest {

        if (tgChatId == null || request.getLink() == null) {
            throw new IncorrectChatOperationRequest(INCORRECT_REQUEST_PARAMS);
        }

        return new LinkResponse(tgChatId, request.getLink());
    }

    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = String.class))}),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = ApiErrorResponse.class))}),
        @ApiResponse(responseCode = "404", description = "Ссылка не найдена",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = ApiErrorResponse.class))})
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public LinkResponse untrackLink(@RequestParam Integer tgChatId,
        @RequestBody RemoveLinkRequest request) throws IncorrectChatOperationRequest, DoesNotExistException {

        if (tgChatId == null || request.getLink() == null) {
            throw new IncorrectChatOperationRequest(INCORRECT_REQUEST_PARAMS);
        }

        if (false) {
            throw new DoesNotExistException("Ссылка не найдена");
        }

        return new LinkResponse(tgChatId, request.getLink());
    }
}
