package com.example.demo.domain;

import com.example.demo.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Geonguk Han
 * @since 2020-04-27
 */
@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    //todo: 1:N 인데 List를 안받네?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; // 주문가격
    private int count; // 주문 수량
}
