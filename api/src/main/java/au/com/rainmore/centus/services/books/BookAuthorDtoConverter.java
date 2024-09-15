package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Author;
import au.com.rainmore.centus.services.books.dto.AuthorDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookAuthorDtoConverter implements Converter<Author, AuthorDto> {

    @Transactional(readOnly = true)
    @Override
    public AuthorDto convert(Author source) {
        return new AuthorDto(
                source.id,
                source.firstname,
                source.middleName,
                source.lastname
        );
    }
}
