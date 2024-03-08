package com.Intent.shop.mapper;



import com.Intent.shop.dto.ProductDTO;
import com.Intent.shop.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapperUtil {
    public static ProductDTO toProductDto(Product product) {
        return new ProductDTO()
                .setId(product.getId())
                .setPrice(product.getPrice())
                .setName(product.getName())
                .setSize(product.getSize())
                .setQuantity(product.getQuantity())
                .setDescription(product.getDescription());
    }

    public static Product toProduct(ProductDTO productDTO) {
        return new Product()
                .setId(productDTO.getId())
                .setSize(productDTO.getSize())
                .setName(productDTO.getName())
                .setPrice(productDTO.getPrice())
                .setQuantity(productDTO.getQuantity())
                .setDescription(productDTO.getDescription());
    }

}
