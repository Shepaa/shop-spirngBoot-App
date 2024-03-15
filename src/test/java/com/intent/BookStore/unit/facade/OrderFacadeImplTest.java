package com.intent.BookStore.unit.facade;

import com.Intent.shop.dto.OrderDTO;
import com.Intent.shop.facade.impl.OrderFacadeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNull;

class OrderFacadeImplTest {

    @InjectMocks
    private OrderFacadeImpl orderFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrderById() {
        Long orderId = 1L;
        OrderDTO result = orderFacade.getOrderById(orderId);
        assertNull(result);
    }
}