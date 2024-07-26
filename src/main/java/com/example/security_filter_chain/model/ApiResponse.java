package com.example.security_filter_chain.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private HttpStatus status;
    private T data;
    private APIError error;

    @Getter
    @Builder
    public static class APIError {
        private String message;
        private String type;
        private Integer code;
        private String error_user_title;
        private String error_user_msg;
    }

    // TODO: go through code (sub code) and confirm it
    @Getter
    public enum ErrorType {
        VALIDATION_ERROR("Validation Error", 400),
        ILLEGAL_ARGUMENT("Illegal Argument", 400),
        METHOD_ARGUMENT_TYPE_MISMATCH("Method Argument Type Mismatch", 400),
        HTTP_MESSAGE_NOT_READABLE("HTTP Message Not Readable", 400),
        SHOPIFY_SERVER_ACTION_FAILED("Shopify Server Action Failed", 400),
        UPDATE_FAILED("Update failed", 400),
        UNAUTHORIZED("Unauthorized", 401),
        NOT_FOUND("Not Found", 404),
        IO_EXCEPTION("IO Exception", 500),
        INVALID_AUTHENTICATION_TOKEN("Invalid Authentication Token", 401),
        AUTHENTICATION_TOKEN_EXPIRED("Authentication Token Expired", 401),
        LIMIT_EXFEED("Limit Exceed", 400),
        FILE_UPLOAD_ERROR("File Upload Error", 505),
        JSON_PROCESSING_ERROR("Error Processing JSON", 500),
        HTTP_REQUEST_ERROR("Error performing HTTP request", 500),
        GRAPHQL_QUERY_NOT_FOUND("GraphQL Query Not Found", 404),
        GRAPHQL_QUERY_FILE_NOT_FOUND("GraphQL File Not Found", 404),
        MISSING_RESOURCE("Missing Resource", 404),
        INTERNAL_SERVER_ERROR("Internal Server Error", 500),
        NULL_VALUE_ERROR("Null Value Error", 500),
        UNKNOWN_ERROR("Unknown Error", 500),
        INVALID_TOKEN("Invalid Token", 401),
        INVALID_REFRESH_TOKEN("Invalid Refresh Token", 401),
        INVALID_ACCESS_TOKEN("Invalid Access Token", 401),
        NO_SUCH_BEAN_FOUND("No Such Bean Found", 404),
        METHOD_NOT_ALLOWED("Method  Not Allowed", 405),
        AUTH_TOKEN_EXPIRED("Auth Token expired", 463),
        INVALID_AUTH_TOKEN("Invalid Auth Token", 467),
        GENERIC_ERROR("Generic Exception", -1),
        BAD_REQUEST("Bad Request", 400);

        private final String type;
        private final Integer code;

        ErrorType(String type, Integer code) {
            this.type = type;
            this.code = code;
        }
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .data(data)
                .error(null)
                .build();
    }

}