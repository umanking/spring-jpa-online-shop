package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.Item;
import com.example.demo.domain.type.OrderStatus;
import com.example.demo.exception.NotEnoughStockException;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Geonguk Han
 * @since 2020-05-10
 */
@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    void 주문_테스트() {
        //given
        Member member = createMember();

        Item book = createBook();

        //when
        int orderCount = 2;
        Long getOrderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(getOrderId);

        assertEquals(OrderStatus.ORDER, order.getOrderStatus(), "상품 주문시 상태가 ORDER");
        assertEquals(1, order.getOrderItems().size(), "주문한 수량이 맞는지 확인");
        assertEquals(orderCount * 10_000, order.getTotalPrice(), "총 주문 가격을 계산한다.");
        assertEquals(8, book.getStockQuantity(), "나머지 책 재고수량이 일치하는지");

    }

    @Test
    void 상품주문_재고_수량_초과_테스트() {
        //given
        Member member = createMember();
        Item book = createBook();

        //when then
        int orderCount = 11;
        Assertions.assertThatExceptionOfType(NotEnoughStockException.class)
                .isThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount));
    }

    @Test
    void 주문_취소_테스트() {
        //given
        Member member = createMember();
        Item book = createBook();

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
        assertEquals(10, book.getStockQuantity());

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("andrew");
        member.setAddress(new Address("seoule", "samjeon-dong", "123-123"));
        em.persist(member);
        return member;
    }

    private Item createBook() {
        Item book = new Book();
        book.setName("노인과 바다");
        book.setPrice(10_000);
        book.addStock(10);
        em.persist(book);
        return book;
    }
}