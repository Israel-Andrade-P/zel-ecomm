package com.zeldev.zel_e_comm.unittests.utils;

import com.zeldev.zel_e_comm.dto.response.OrderItemResponse;
import com.zeldev.zel_e_comm.entity.CartItemEntity;
import com.zeldev.zel_e_comm.entity.OrderEntity;
import com.zeldev.zel_e_comm.entity.OrderItemEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.util.OrderItemUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemUtilsTest {

    @Test
    @DisplayName(
            """
                    GIVEN: a set of cart items and a order
                    WHEN: createOrderItems is called
                    THEN: maps cart items to order items                   
                    """
    )
    void greenPath() {
        ProductEntity product1 = new ProductEntity();
        product1.setName("TV");
        product1.setPrice(new BigDecimal("50.00"));
        product1.setDiscount(10);
        ProductEntity product2 = new ProductEntity();
        product2.setName("Fridge");
        product2.setPrice(new BigDecimal("499.00"));
        product2.setDiscount(15);
        ProductEntity product3 = new ProductEntity();
        product3.setName("Washing Machine");
        product3.setPrice(new BigDecimal("200.00"));
        product3.setDiscount(0);

        CartItemEntity cartItem1 = new CartItemEntity();
        cartItem1.setPrice(new BigDecimal("50.00"));
        cartItem1.setQuantity(2);
        cartItem1.setProduct(product1);
        CartItemEntity cartItem2 = new CartItemEntity();
        cartItem2.setPrice(BigDecimal.valueOf(499));
        cartItem2.setQuantity(5);
        cartItem2.setProduct(product2);
        CartItemEntity cartItem3 = new CartItemEntity();
        cartItem3.setPrice(BigDecimal.valueOf(499));
        cartItem3.setQuantity(5);
        cartItem3.setProduct(product3);

        OrderEntity order = new OrderEntity();

        Set<CartItemEntity> cartItems = Set.of(cartItem1, cartItem2, cartItem3);

        List<OrderItemEntity> orderItems = OrderItemUtils.createOrderItems(cartItems, order);

        assertEquals(cartItems.size(), orderItems.size());

        OrderItemEntity orderItem = orderItems.stream().filter(item -> item.getProductName().equals("TV")).findFirst().orElseThrow();
        assertEquals(product1.getName(), orderItem.getProductName());
        assertEquals(cartItem1.getQuantity(), orderItem.getQuantity());
        assertEquals(cartItem1.getPrice(), orderItem.getPrice());
        assertEquals(new BigDecimal("5.00"), orderItem.getDiscountAmount());
        assertEquals(product1, orderItem.getProduct());
        assertEquals(order, orderItem.getOrder());

        OrderItemEntity orderItem2 = orderItems.stream().filter(item -> item.getProductName().equals("Fridge")).findFirst().orElseThrow();
        assertEquals(product2.getName(), orderItem2.getProductName());
        assertEquals(cartItem2.getQuantity(), orderItem2.getQuantity());
        assertEquals(cartItem2.getPrice(), orderItem2.getPrice());
        assertEquals(new BigDecimal("74.85"), orderItem2.getDiscountAmount());
        assertEquals(product2, orderItem2.getProduct());
        assertEquals(order, orderItem2.getOrder());

        OrderItemEntity orderItem3 = orderItems.stream().filter(item -> item.getProductName().equals("Washing Machine")).findFirst().orElseThrow();
        assertEquals(new BigDecimal("0.00"), orderItem3.getDiscountAmount());

        assertEquals(3, order.getOrderItems().size());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a order item entity
                    WHEN: toOrderItemResponse is called
                    THEN: maps order item entity to response                   
                    """
    )
    void toOrderItemResponseTest() {
        OrderItemEntity entity = OrderItemEntity.builder()
                .productName("Phone")
                .price(new BigDecimal("500"))
                .quantity(1)
                .discountAmount(new BigDecimal("50"))
                .build();

        OrderItemResponse response =
                OrderItemUtils.toOrderItemResponse(entity);

        assertEquals("Phone", response.productName());
        assertEquals(new BigDecimal("500"), response.price());
    }
}
