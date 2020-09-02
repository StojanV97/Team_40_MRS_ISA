package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.PriceBook;
import org.springframework.data.repository.CrudRepository;

public interface PriceBookRepository extends CrudRepository<PriceBook,Long> {
}
