package au.com.rainmore.centus.controllers.books;


import au.com.rainmore.centus.services.books.BookCategoryService;
import au.com.rainmore.centus.services.books.dto.CategoryDto;
import au.com.rainmore.centus.services.core.dto.PageDto;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Book Category APIs")
@RestController
@RequestMapping("/api/books/categories")
public class BookCategoryController {

    private final BookCategoryService bookcategoryService;

    @Autowired
    public BookCategoryController(BookCategoryService bookcategoryService) {
        this.bookcategoryService = bookcategoryService;
    }

    @Operation(summary = "Retrieve book categories",
            description = "This API returns books categories.")
    @ApiResponse(
            responseCode = "200",
            description = "The response payload contains the book category details.",
            content = @Content(schema = @Schema(implementation = CategoryDto.class)))
    @GetMapping("")
    public PageDto<CategoryDto> list(
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable) {

        Optional<Node> node = Optional.ofNullable(search).map(searchStr -> new RSQLParser().parse(searchStr));

        return new PageDto<>(bookcategoryService.findAllDto(pageable));
    }
}
