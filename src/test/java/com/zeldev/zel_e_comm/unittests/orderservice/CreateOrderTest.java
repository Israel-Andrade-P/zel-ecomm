package com.zeldev.zel_e_comm.unittests.orderservice;

import com.zeldev.zel_e_comm.dto.request.OrderRequest;
import com.zeldev.zel_e_comm.entity.*;
import com.zeldev.zel_e_comm.enumeration.OrderStatus;
import com.zeldev.zel_e_comm.exception.CartIsEmptyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateOrderTest extends OrderServiceBaseTest{

    @Test
    @DisplayName(
            """
                    GIVEN: a order request 
                    WHEN: createOrder is called
                    THEN: builds and persists a new order entity
                    AND: return a order response
                    """
    )
    void greenPath() {
        OrderRequest request = new OrderRequest(UUID.randomUUID().toString());
        UserEntity user = new UserEntity();
        user.setEmail("mike@gmail");
        CartEntity cart = new CartEntity();
        CartItemEntity item1 = new CartItemEntity();
        ProductEntity product1 = new ProductEntity();
        item1.setQuantity(1);
        item1.setProduct(product1);
        CartItemEntity item2 = new CartItemEntity();
        ProductEntity product2 = new ProductEntity();
        item2.setProduct(product2);
        item2.setQuantity(2);
        cart.addItem(item1);
        cart.addItem(item2);
        LocationEntity location = new LocationEntity();
        location.setPublicId(UUID.fromString(request.locationPublicId()));

        when(authUtils.getLoggedInUser()).thenReturn(user);
        when(cartService.getCartByEmail(user.getEmail())).thenReturn(cart);
        when(locationService.getByPublicIdAndUserEmail(request.locationPublicId(), user.getEmail())).thenReturn(location);
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(invocation -> {
            OrderEntity order = invocation.getArgument(0);
            order.setCreatedAt(LocalDateTime.now());
            return order;
        });

        var response = orderService.createOrder(request);

        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);

        verify(orderRepository).save(captor.capture());

        var order = captor.getValue();

        assertNotNull(order.getPublicId());
        assertEquals(OrderStatus.PENDING_PAYMENT, order.getStatus());
        assertEquals(user, order.getUser());
        assertEquals(location, order.getLocation());

        assertEquals(order.getPublicId(), response.orderId());
        assertEquals(user.getEmail(), response.userEmail());
        assertEquals(order.getStatus(), response.status());
        assertEquals(order.getLocation().getPublicId().toString(), response.locationPublicId());

        verify(authUtils).getLoggedInUser();
        verify(cartService).getCartByEmail(user.getEmail());
        verify(locationService).getByPublicIdAndUserEmail(request.locationPublicId(), user.getEmail());
        verify(orderRepository).save(any(OrderEntity.class));
        verify(orderItemService).createOrderItems(eq(cart.getCartItems()), any());
        verify(productService, times(2)).decreaseStock(any(), any());

        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a order request 
                    WHEN: createOrder is called
                    THEN: verify cart is empty
                    AND: throws exception
                    """
    )
    void cartEmpty() {
        OrderRequest request = new OrderRequest(UUID.randomUUID().toString());
        UserEntity user = new UserEntity();
        user.setEmail("mike@gmail");
        CartEntity cart = new CartEntity();

        when(authUtils.getLoggedInUser()).thenReturn(user);
        when(cartService.getCartByEmail(user.getEmail())).thenReturn(cart);

        assertThrows(CartIsEmptyException.class, () -> orderService.createOrder(request));

        verify(orderRepository, never()).save(any());
        verify(orderItemService, never()).createOrderItems(any(), any());
    }
}
