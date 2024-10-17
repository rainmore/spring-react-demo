package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.domains.books.Category;
import au.com.rainmore.centus.services.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends BaseRepository<Category, Long> {

}
