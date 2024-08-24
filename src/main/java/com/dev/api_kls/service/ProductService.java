package com.dev.api_kls.service;

import com.dev.api_kls.model.Product;
import com.dev.api_kls.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Product update(Long id, Product product) {
        Optional<Product> productToBeUpdated = getById(id);

        if (productToBeUpdated.isEmpty())
            return null;

        Product existingProduct = productToBeUpdated.get();

        BeanUtils.copyProperties(product, existingProduct, getNullPropertyNames(product));

        existingProduct.setId(id);
        existingProduct.setActiveInMarket(true);

        return repository.save(productToBeUpdated.get());
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<Product> productToBeUpdated = getById(id);

        if (productToBeUpdated.isEmpty())
            return false;

        productToBeUpdated.get().setActiveInMarket(false);

        repository.save(productToBeUpdated.get());

        return productToBeUpdated.get().isActiveInMarket();
    }


    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

}
