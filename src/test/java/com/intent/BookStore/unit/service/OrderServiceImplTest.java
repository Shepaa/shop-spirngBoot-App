package com.intent.BookStore.unit.service;

import com.Intent.shop.exception.OrderNotFoundException;
import com.Intent.shop.model.Order;
import com.Intent.shop.repository.OrderRepository;
import com.Intent.shop.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.Intent.shop.util.ExceptionMessageUtil.ORDER_NOT_FOUND_BY_ID_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void getOrderByIdTest() {
        Long orderId = 1L;
        Order order = new Order().setId(orderId).setQuantity(2).setTotalPrice(BigDecimal.valueOf(50.00));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        Order result = orderService.getOrderById(orderId);
        assertEquals(order, result);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void getOrderByIdWithNotFoundExceptionTest() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(orderId));
        assertEquals(String.format(ORDER_NOT_FOUND_BY_ID_ERROR_MESSAGE, orderId), exception.getMessage());
        verify(orderRepository, times(1)).findById(orderId);
    }
}
