package com.Intent.shop.facade.impl;


import com.Intent.shop.dto.OrderDTO;
import com.Intent.shop.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {


    @Override
    public OrderDTO getOrderById(Long id) {
        return null;
    }
}
