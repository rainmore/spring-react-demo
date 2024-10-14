package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Category;
import au.com.rainmore.centus.services.books.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<Category> findAll(Pageable pageable) {
        return bookCategoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<CategoryDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(bookCategoryDtoConverter::convert);
    }

    public void save(Category category) {
        bookCategoryRepository.save(category);
    }
}
