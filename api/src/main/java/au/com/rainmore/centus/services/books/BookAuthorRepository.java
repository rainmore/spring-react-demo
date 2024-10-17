package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Author;
import au.com.rainmore.centus.services.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepository extends BaseRepository<Author, Long> {

}
