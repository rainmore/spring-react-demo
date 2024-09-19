package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Category;
import au.com.rainmore.centus.services.books.dto.CategoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookCategoryDtoConverter implements Converter<Category, CategoryDto> {

    @Transactional(readOnly = true)
    @Override
    public CategoryDto convert(Category source) {
        return new CategoryDto(
                source.getId(),
                source.getName(),
                source.getDescription()
        );
    }
}
