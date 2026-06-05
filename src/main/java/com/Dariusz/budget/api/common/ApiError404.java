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
@ApiResponse(responseCode = "404", description = "Nie znaleziono zasobu",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProblemDetail.class),
                examples = @ExampleObject(value = """
                        {
                          "title": "Nie znaleziono zasobu",
                          "status": 404,
                          "detail": "Konto o id 999 nie istnieje",
                          "instance": "/api/accounts/999"
                        }
                        """)
        )
)
public @interface ApiError404 {}