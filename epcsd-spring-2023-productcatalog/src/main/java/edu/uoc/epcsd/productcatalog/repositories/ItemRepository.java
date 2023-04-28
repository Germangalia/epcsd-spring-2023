package edu.uoc.epcsd.productcatalog.repositories;

import edu.uoc.epcsd.productcatalog.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
