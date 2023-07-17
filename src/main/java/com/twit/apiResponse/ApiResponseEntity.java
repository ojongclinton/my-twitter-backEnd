package com.twit.apiResponse;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponseEntity {
    private Code code;
    private HttpStatus status;
    private ResultInterface result;
}
