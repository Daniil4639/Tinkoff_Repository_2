package edu.java.response;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

// Классы GitHubResponse и StackOverFlowResponse используются для
// конвертации данных в универсальный класс ResourceResponse, ибо у каждого
// из ресурсов свои поля в JSON файлах

// Возможно, придется изменить логику в процессе дальнейшей разработки,
// но на данном этапе это одно из самых простых, на мой взгляд, решений
@Getter
@Setter
public class ResourceResponse {
    private String resourceName;
    private String resourceUrl;
    private OffsetDateTime creationDate;
    private OffsetDateTime lastUpdateDate;

    public ResourceResponse(String name, String url, OffsetDateTime creation, OffsetDateTime lastUpdate) {
        this.resourceName = name;
        this.resourceUrl = url;
        this.creationDate = creation;
        this.lastUpdateDate = lastUpdate;
    }
}
