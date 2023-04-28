package edu.uoc.epcsd.productcatalog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.uoc.epcsd.productcatalog.entities.Item;
import edu.uoc.epcsd.productcatalog.entities.ItemStatus;
import edu.uoc.epcsd.productcatalog.entities.Product;
import edu.uoc.epcsd.productcatalog.kafka.ProductMessage;
import edu.uoc.epcsd.productcatalog.repositories.ItemRepository;
import edu.uoc.epcsd.productcatalog.repositories.ProductRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private KafkaTemplate<String, ProductMessage> productKafkaTemplate;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        log.trace("getAllProducts");

        return productRepository.findAll();
    }

    // add the code for the missing operations here
    
    // crear product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        log.trace("createProduct");
        return productRepository.save(product);
    }

    // crear item de product
    @PostMapping("/{productId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@PathVariable Long productId, @RequestBody Item item) {
        log.trace("createItem");
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        item.setProduct(product);
        return itemRepository.save(item);
    }
    
    // eliminar product
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        log.trace("deleteProduct");
        productRepository.deleteById(id);
    }
    
    // canviar l’estat d’un item de product a operatiu/no-operatiu
    @PutMapping("/items/{itemId}/status")
    @ResponseStatus(HttpStatus.OK)
    public Item changeItemStatus(@PathVariable Long itemId, @RequestBody ItemStatus status) {
        log.trace("changeItemStatus");
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setStatus(status);
        return itemRepository.save(item);
    }
    
    // consulta de products per nom
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsByName(@PathVariable String name) {
        log.trace("getProductsByName");
        return productRepository.findByName(name);
    }

    // consulta de products per catalog
    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsByCategoryId(@PathVariable Long categoryId) {
        log.trace("getProductsByCategoryId");
        return productRepository.findByCategoryId(categoryId);
    }

    // consulta del detall d’un product
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductDetails(@PathVariable Long id) {
        log.trace("getProductDetails");
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // consulta del detall d’un item
    @GetMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Item getItemDetails(@PathVariable Long itemId) {
        log.trace("getItemDetails");
        return itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
    }

}
