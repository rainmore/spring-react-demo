package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Category;
import au.com.rainmore.centus.domains.books.QBook;
import au.com.rainmore.centus.services.books.dto.CategoryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.perplexhub.rsql.RSQLQueryDslSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookCategoryService {

    private final BookCategoryRepository   bookCategoryRepository;
    private final BookCategoryDtoConverter bookCategoryDtoConverter;

    @Autowired
    public BookCategoryService(BookCategoryRepository bookCategoryRepository,
                               BookCategoryDtoConverter bookCategoryDtoConverter) {
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookCategoryDtoConverter = bookCategoryDtoConverter;
    }

    public Page<Category> findAll(Optional<BooleanExpression> booleanExpression,
                                  Pageable pageable) {
        return booleanExpression.map(be -> bookCategoryRepository.findAll(be, pageable))
                .orElseGet(() -> bookCategoryRepository.findAll(pageable));
    }

    @Transactional(readOnly = true)
    public Page<CategoryDto> findAllDto(String search, Pageable pageable) {
        Optional<BooleanExpression> criteria = Optional.ofNullable(search)
                .map(s -> RSQLQueryDslSupport.toPredicate(s, QBook.book));
        return findAll(criteria, pageable).map(bookCategoryDtoConverter::convert);
    }

    public void save(Category category) {
        bookCategoryRepository.save(category);
    }
}
