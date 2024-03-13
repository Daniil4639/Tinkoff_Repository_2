package edu.java.controller;

import edu.java.api_exceptions.DoesNotExistException;
import edu.java.api_exceptions.IncorrectChatOperationRequest;
import edu.java.jdbc.JdbcChatService;
import edu.java.response.api.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JdbcChatService chatService;

    private final static String ID_MAPPING = "/{id}";
    private final static String INCORRECT_REQUEST_PARAMS = "Некорректные параметры запроса";

    @Operation(summary = "Зарегистрировать чат")
    @ApiResponse(responseCode = "200", description = "Чат зарегистрирован")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                 content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    @PostMapping(ID_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public String chatRegistration(@PathVariable Integer id) throws
        IncorrectChatOperationRequest {

        if (id == null) {
            throw new IncorrectChatOperationRequest(INCORRECT_REQUEST_PARAMS);
        }

        return chatService.addChat(id);
    }

    @Operation(summary = "Удалить чат")
    @ApiResponse(responseCode = "200", description = "Чат успешно удален")
    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                 content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    @ApiResponse(responseCode = "404", description = "Чат не найден",
                 content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    @DeleteMapping(ID_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public String chatDelete(@PathVariable Integer id) throws
        IncorrectChatOperationRequest, DoesNotExistException {

        if (id == null) {
            throw new IncorrectChatOperationRequest(INCORRECT_REQUEST_PARAMS);
        }

        if (!chatService.deleteChat(id)) {
            throw new DoesNotExistException("Чат не существует");
        }

        return "Чат успешно удалён";
    }
}
