package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Book;
import au.com.rainmore.centus.services.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends BaseRepository<Book, Long> {

}
