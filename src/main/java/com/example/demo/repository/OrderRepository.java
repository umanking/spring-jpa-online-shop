package com.example.demo.repository;

import com.example.demo.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Geonguk Han
 * @since 2020-05-08
 */
@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // todo: 검색조건 동적 쿼리로 작성
    /*public List<Order> findAll(OrderSearch orderSearch) {

    }*/
}
