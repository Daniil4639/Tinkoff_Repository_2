package edu.java.controller;

import edu.java.api_exceptions.DoesNotExistException;
import edu.java.api_exceptions.IncorrectChatOperationRequest;
import edu.java.response.api.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat")
public class ScrapperChatController {

    private final static String INCORRECT_REQUEST_PARAMS = "Некорректные параметры запроса";

    @Operation(summary = "Зарегистрировать чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = String.class))}),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = ApiErrorResponse.class))})
    })
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String chatRegistration(@PathVariable Integer id) throws
        IncorrectChatOperationRequest {

        if (id == null) {
            throw new IncorrectChatOperationRequest(INCORRECT_REQUEST_PARAMS);
        }

        return "Чат зарегистрирован";
    }

    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат успшно удален",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = String.class))}),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = ApiErrorResponse.class))}),
        @ApiResponse(responseCode = "404", description = "Чат не найден",
                     content = {@Content(mediaType = "application/json",
                                         schema = @Schema(implementation
                                             = ApiErrorResponse.class))})
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String chatDelete(@PathVariable Integer id) throws
        IncorrectChatOperationRequest, DoesNotExistException {

        if (id == null) {
            throw new IncorrectChatOperationRequest(INCORRECT_REQUEST_PARAMS);
        }

        if (false) {
            throw new DoesNotExistException("Чат не существует");
        }

        return "Чат успешно удалён";
    }
}
