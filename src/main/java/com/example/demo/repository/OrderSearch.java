package com.example.demo.repository;

import com.example.demo.domain.type.OrderStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Geonguk Han
 * @since 2020-05-10
 */
@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus; // [ORDER, CANCEL]
}
