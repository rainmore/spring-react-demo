package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Book;
import au.com.rainmore.centus.domains.books.QBook;
import au.com.rainmore.centus.services.books.dto.BookDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.perplexhub.rsql.RSQLQueryDslSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository   bookRepository;
    private final BookDtoConverter bookDtoConverter;

    public BookService(BookRepository bookRepository, BookDtoConverter bookDtoConverter) {
        this.bookRepository = bookRepository;
        this.bookDtoConverter = bookDtoConverter;
    }

    public Page<Book> findAll(Optional<BooleanExpression> booleanExpression,
                              Pageable pageable) {
        return booleanExpression.map(be -> bookRepository.findAll(be, pageable))
                .orElseGet(() -> bookRepository.findAll(pageable));
    }

    @Transactional(readOnly = true)
    public Page<BookDto> findAllDto(String search, Pageable pageable) {
        Optional<BooleanExpression> criteria = Optional.ofNullable(search)
                .map(s -> RSQLQueryDslSupport.toPredicate(s, QBook.book));
        return findAll(criteria, pageable).map(bookDtoConverter::convert);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }
}
