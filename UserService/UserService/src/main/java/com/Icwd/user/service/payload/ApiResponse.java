package com.Icwd.user.service.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.http.HttpStatus;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class ApiResponse {
//    private String message;
//    private boolean success;
//    private HttpStatus status;
//}
import org.springframework.http.HttpStatus;

public class ApiResponse {
    private String message;
    private boolean success;
    private HttpStatus status;

    // Private constructor to prevent direct instantiation from outside
    private ApiResponse(Builder builder) {
        this.message = builder.message;
        this.success = builder.success;
        this.status = builder.status;
    }

    // Static Builder class to construct ApiResponse objects
    public static class Builder {
        private String message;
        private boolean success;
        private HttpStatus status;

        // Builder method to set message
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        // Builder method to set success
        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        // Builder method to set status
        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        // Build method to create the final ApiResponse object
        public ApiResponse build() {
            return new ApiResponse(this);
        }
    }

    // Static method to get a new builder instance
    public static Builder builder() {
        return new Builder();
    }

    // Getters for the fields
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

