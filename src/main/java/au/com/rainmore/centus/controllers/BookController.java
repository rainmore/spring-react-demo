package au.com.rainmore.centus.controllers;

import au.com.rainmore.centus.domains.Book;
import au.com.rainmore.centus.services.dto.PageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Book APIs")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Operation(summary = "Retrieve books",
            description = "This API returns books.")
    @ApiResponse(
            responseCode = "200",
            description = "The response payload contains the book details.",
            content = @Content(schema = @Schema(implementation = Book.class)))
    @GetMapping("")
    public PageDto<Book> list(Pageable pageable) {
        return new PageDto<>(pageable);
    }

}
