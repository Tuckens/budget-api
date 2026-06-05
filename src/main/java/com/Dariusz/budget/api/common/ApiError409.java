package com.Dariusz.budget.api.common;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ProblemDetail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "409", description = "Konflikt",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProblemDetail.class),
                examples = @ExampleObject(value = """
                        {
                          "title": "Konflikt",
                          "status": 409,
                          "detail": "Nie można usunąć konta z przypisanymi transakcjami",
                          "instance": "/api/accounts/1"
                        }
                        """)
        )
)
public @interface ApiError409 {}