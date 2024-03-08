package com.Intent.shop.service.impl;


import com.Intent.shop.exception.OrderNotFoundException;
import com.Intent.shop.model.Order;
import com.Intent.shop.repository.OrderRepository;
import com.Intent.shop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.Intent.shop.util.ExceptionMessageUtil.ORDER_NOT_FOUND_BY_ID_ERROR_MESSAGE;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_BY_ID_ERROR_MESSAGE, id)));
    }
}
