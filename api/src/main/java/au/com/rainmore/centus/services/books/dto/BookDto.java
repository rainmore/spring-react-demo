package au.com.rainmore.centus.services.books.dto;

import java.time.LocalDate;
import java.util.List;

public record BookDto(
        Long id,
        CategoryDto category,
        String name,
        LocalDate publicationDate,
        List<AuthorDto> authors
) {

}
