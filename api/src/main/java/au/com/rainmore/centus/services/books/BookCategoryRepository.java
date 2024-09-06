package au.com.rainmore.centus.services.books;

import au.com.rainmore.centus.models.books.Category;
import au.com.rainmore.centus.services.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends BaseRepository<Category, Long> {
}
