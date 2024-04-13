package edu.java.controller;

import edu.java.db_services.LinksService;
import edu.java.exceptions.DoesNotExistException;
import edu.java.exceptions.IncorrectRequest;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.ApiErrorResponse;
import edu.java.responses.LinkResponse;
import edu.java.responses.LinkResponseList;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    private final LinksService linksService;

    private final Counter requestsCounter;

    public ScrapperLinksController(LinksService linksService, MeterRegistry meterRegistry) {
        this.linksService = linksService;
        requestsCounter = meterRegistry.counter("processed_requests_count");
    }

    @Operation(summary = "Получить все отслеживаемые ссылки")
    @ApiResponse(responseCode = "200", description = "Ссылки успешно получены")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                 content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public LinkResponseList getLinks(@RequestParam Integer tgChatId) throws IncorrectRequest {

        requestsCounter.increment();
        return linksService.getLinksByChat(tgChatId);
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                 content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LinkResponse trackLink(@RequestParam Integer tgChatId,
        @RequestBody AddLinkRequest request) throws IncorrectRequest {

        linksService.addLink(tgChatId, request.getLink());
        requestsCounter.increment();
        return new LinkResponse(tgChatId, request.getLink());
    }

    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                 content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "Ссылка не найдена",
                 content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public LinkResponse untrackLink(@RequestParam Integer tgChatId,
        @RequestBody RemoveLinkRequest request) throws IncorrectRequest,
        DoesNotExistException {

        linksService.deleteLink(tgChatId, request.getLink());
        requestsCounter.increment();
        return new LinkResponse(tgChatId, request.getLink());
    }
}
