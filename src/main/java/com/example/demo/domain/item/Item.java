package com.example.demo.domain.item;

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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

}
