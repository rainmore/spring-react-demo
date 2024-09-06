package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.models.books.Author;
import au.com.rainmore.centus.services.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepository extends BaseRepository<Author, Long> {

}
