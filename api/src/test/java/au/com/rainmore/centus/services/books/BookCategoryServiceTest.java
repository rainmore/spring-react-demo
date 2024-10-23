package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Book;
import au.com.rainmore.centus.domains.books.Category;
import au.com.rainmore.centus.services.books.dto.BookDto;
import au.com.rainmore.centus.services.books.dto.CategoryDto;
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
class BookCategoryServiceTest {

    private BookCategoryService bookCategoryService;

    @Mock
    private BookCategoryRepository bookCategoryRepository;

    @Mock
    private BookCategoryDtoConverter bookCategoryDtoConverter;

    @BeforeEach
    void setUp() {
        bookCategoryDtoConverter = new BookCategoryDtoConverter();
        bookCategoryService = new BookCategoryService(
                bookCategoryRepository,
                bookCategoryDtoConverter);
    }

    @Test
    void test_findAllDto_without_rsql() {
        Pageable pageable = PagingUtils.DEFAULT_PAGEABLE;
        Page<Category> page = new PageImpl<>(List.of(), pageable, 0);

//        String search = null;
        String search = "name==fiction";
//        String search = "id=in=(110020,110022);name==fiction";


        BDDMockito.given(bookCategoryRepository.findAll(eq(pageable))).willReturn(page);
        Page<CategoryDto> result = bookCategoryService.findAllDto(search, pageable);

        Assertions.assertThat(result)
                .isInstanceOf(Page.class)
        ;
    }

    @Test
    void save() {
    }
}
