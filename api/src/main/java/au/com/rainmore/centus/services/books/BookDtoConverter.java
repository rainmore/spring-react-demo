package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Book;
import au.com.rainmore.centus.services.books.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookDtoConverter implements Converter<Book, BookDto> {

    private final BookAuthorDtoConverter   bookAuthorDtoConverter;
    private final BookCategoryDtoConverter bookCategoryDtoConverter;

    @Autowired
    public BookDtoConverter(
            BookAuthorDtoConverter bookAuthorDtoConverter,
            BookCategoryDtoConverter bookCategoryDtoConverter) {
        this.bookAuthorDtoConverter = bookAuthorDtoConverter;
        this.bookCategoryDtoConverter = bookCategoryDtoConverter;
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto convert(Book source) {
        return new BookDto(
                source.getId(),
                this.bookCategoryDtoConverter.convert(source.getCategory()),
                source.getName(),
                source.getPublicationDate(),
                source.getAuthors().stream().map(bookAuthorDtoConverter::convert).toList()
        );
    }
}
