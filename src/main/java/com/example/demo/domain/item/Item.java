package com.example.demo.domain.item;

import com.example.demo.domain.Category;
import com.example.demo.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Geonguk Han
 * @since 2020-04-27
 */
@Entity
@Getter
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

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //===비즈니스로직===//
    /**
     * 재고수량 증가
     */
    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

    /**
     * 재고수량 감소
     */
    public void removeStock(int quantity) {
        final int restStock = stockQuantity - quantity;

        if (restStock < 0) {
            throw new NotEnoughStockException("재고 수량이 부족합니다.");
        }

        this.stockQuantity = restStock;
    }
}
