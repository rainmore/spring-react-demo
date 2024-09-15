package au.com.rainmore.centus.controllers.books;

import au.com.rainmore.centus.controllers.BaseRestController;
import au.com.rainmore.centus.domains.books.Book;
import au.com.rainmore.centus.services.books.BookService;
import au.com.rainmore.centus.services.books.dto.BookDto;
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


@Tag(name = "Book APIs")
@RestController
@RequestMapping("/api/books")
public class BookController extends BaseRestController {

    private final BookService bookService;

    @Autowired
    public BookController(
            BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Retrieve books",
            description = "This API returns books.")
    @ApiResponse(
            responseCode = "200",
            description = "The response payload contains the book details.",
            content = @Content(schema = @Schema(implementation = Book.class)))
    @GetMapping("")
    public PageDto<BookDto> list(
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable) {

        Optional<Node> node = Optional.ofNullable(search).map(searchStr -> new RSQLParser().parse(searchStr));

        return bookService.findAllDto(pageable);
    }

}
