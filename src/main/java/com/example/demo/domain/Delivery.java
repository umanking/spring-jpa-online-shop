package com.example.demo.domain;

import com.example.demo.domain.type.DeliveryStatus;
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
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
