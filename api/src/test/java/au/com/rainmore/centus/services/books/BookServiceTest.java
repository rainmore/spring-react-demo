package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Book;
import au.com.rainmore.centus.services.books.dto.BookDto;
import au.com.rainmore.centus.utils.PagingUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookDtoConverter bookDtoConverter;

    @BeforeEach
    void setUp() {
        bookDtoConverter = new BookDtoConverter(
                new BookAuthorDtoConverter(),
                new BookCategoryDtoConverter());
        bookService = new BookService(
                bookRepository,
                bookDtoConverter);
    }

    @Test
    void test_findAllDto_without_rsql() {
        Pageable pageable = PagingUtils.DEFAULT_PAGEABLE;
        Page<Book> page = new PageImpl<>(List.of(), pageable, 0);
        BDDMockito.given(bookRepository.findAll(eq(pageable))).willReturn(page);
        Page<BookDto> result = bookService.findAllDto(null, pageable);

        Assertions.assertThat(result)
                .isInstanceOf(Page.class)
        ;
    }

    @Test
    void save() {
    }
}
