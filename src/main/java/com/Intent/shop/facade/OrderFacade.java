package com.Intent.shop.facade;


import com.Intent.shop.dto.OrderDTO;

public interface OrderFacade {


    OrderDTO getOrderById(Long id);
}
