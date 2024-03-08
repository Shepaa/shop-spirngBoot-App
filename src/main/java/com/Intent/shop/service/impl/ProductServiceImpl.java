package com.Intent.shop.service.impl;


import com.Intent.shop.exception.ProductNotFoundException;
import com.Intent.shop.model.Product;
import com.Intent.shop.repository.ProductRepository;
import com.Intent.shop.service.ProductService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.Intent.shop.util.ExceptionMessageUtil.PRODUCT_NOT_FOUND_BY_ID_ERROR_MESSAGE;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> getAllProducts(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("id"));
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_BY_ID_ERROR_MESSAGE, id)));
    }


    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        checkIfProductNotExist(id);
        updatedProduct.setId(id);
        return productRepository.save(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        checkIfProductNotExist(id);
        productRepository.deleteById(id);
    }



    private Predicate buildAuthorNamePredicate(Root<Product> root, CriteriaBuilder criteriaBuilder, String authorName) {
        if (authorName != null && !authorName.isEmpty()) {
            return criteriaBuilder.equal(root.get("authorName"), authorName);
        }
        return null;
    }

    private Predicate buildGenrePredicate(Root<Product> root, CriteriaBuilder criteriaBuilder, String genre) {
        if (genre != null && !genre.isEmpty()) {
            return criteriaBuilder.equal(root.get("genre"), genre);
        }
        return null;
    }

    private Predicate buildPricePredicate(Root<Product> root, CriteriaBuilder criteriaBuilder, BigDecimal minPrice, BigDecimal maxPrice) {
        Predicate pricePredicate = null;
        if(minPrice != null && maxPrice != null) {
            pricePredicate = criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        } else if(minPrice != null) {
            pricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
        } else if(maxPrice != null) {
            pricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        }
        return pricePredicate;
    }

    private Predicate buildQuantityPredicate(Root<Product> root, CriteriaBuilder criteriaBuilder, int quantity) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), quantity);
    }

    private void checkIfProductNotExist(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_BY_ID_ERROR_MESSAGE, id));
        }
    }
}
