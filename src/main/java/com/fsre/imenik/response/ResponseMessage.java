package com.fsre.imenik.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private HttpStatus httpStatus;
    private Integer code;
    private String message;
    private Date timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<?> items;

    public ResponseMessage(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.timestamp = new Date();
    }

    public ResponseMessage(HttpStatus httpStatus, Integer code, String message, List<?> items) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.items = items;
        this.timestamp = new Date();
    }
}
