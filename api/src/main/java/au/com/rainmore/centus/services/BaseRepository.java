package au.com.rainmore.centus.services;

import au.com.rainmore.centus.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends Model, ID extends Serializable>
    extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {

}
