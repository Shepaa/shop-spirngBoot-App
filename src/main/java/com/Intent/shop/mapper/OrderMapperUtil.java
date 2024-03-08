package com.Intent.shop.mapper;


import com.Intent.shop.dto.OrderDTO;
import com.Intent.shop.model.Order;
import com.Intent.shop.model.Product;
import com.Intent.shop.model.User;
import lombok.experimental.UtilityClass;



@UtilityClass
public class OrderMapperUtil {
    public static OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO()
                .setId(order.getId())
                .setQuantity(order.getQuantity())
                .setUserId(order.getUser().getId())
                .setTotalPrice(order.getTotalPrice())
                .setProductId(order.getProduct().getId());
        return orderDTO;
    }

    public static Order toOrder(Product product, User user, OrderDTO orderDTO) {
        return new Order()
                .setUser(user)
                .setId(orderDTO.getId())
                .setQuantity(orderDTO.getQuantity())
                .setTotalPrice(orderDTO.getTotalPrice())
                .setProduct(product);
    }
}
