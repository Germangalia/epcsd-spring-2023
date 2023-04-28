package edu.uoc.epcsd.productcatalog.controllers;

import edu.uoc.epcsd.productcatalog.entities.Category;
import edu.uoc.epcsd.productcatalog.repositories.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories() {
        log.trace("getAllCategories");

        return categoryRepository.findAll();
    }

    // add the code for the missing operations here

    // crear catalog/subcatalog
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody Category category) {
        log.trace("createCategory");
        return categoryRepository.save(category);
    }

    // consulta de catalog/subcatalog per nom
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getCategoriesByName(@PathVariable String name) {
        log.trace("getCategoriesByName");
        return categoryRepository.findByName(name);
    }

    // consulta de catalog/subcatalog per descripció
    @GetMapping("/description/{description}")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getCategoriesByDescription(@PathVariable String description) {
        log.trace("getCategoriesByDescription");
        return categoryRepository.findByDescription(description);
    }

    // consulta de catalog/subcatalog por secció “pare”
    @GetMapping("/parent/{parentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getCategoriesByParentId(@PathVariable Long parentId) {
        log.trace("getCategoriesByParentId");
        return categoryRepository.findByParentId(parentId);
    }
}
