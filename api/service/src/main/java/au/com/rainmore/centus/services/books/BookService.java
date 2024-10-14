package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Book;
import au.com.rainmore.centus.services.books.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository   bookRepository;
    private final BookDtoConverter bookDtoConverter;

    public BookService(BookRepository bookRepository, BookDtoConverter bookDtoConverter) {
        this.bookRepository = bookRepository;
        this.bookDtoConverter = bookDtoConverter;
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<BookDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(bookDtoConverter::convert);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }
}
