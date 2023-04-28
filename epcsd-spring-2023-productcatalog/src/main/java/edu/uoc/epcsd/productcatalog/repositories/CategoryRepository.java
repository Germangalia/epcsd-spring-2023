package edu.uoc.epcsd.productcatalog.repositories;

import edu.uoc.epcsd.productcatalog.entities.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByName(String name);
    List<Category> findByDescription(String description);
    List<Category> findByParentId(Long parentId);
}
