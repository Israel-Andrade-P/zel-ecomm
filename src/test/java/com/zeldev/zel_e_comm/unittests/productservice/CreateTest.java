package com.zeldev.zel_e_comm.unittests.productservice;

import com.zeldev.zel_e_comm.domain.UserSecurity;
import com.zeldev.zel_e_comm.dto.request.ProductDTO;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateTest extends ProductServiceBaseTest{

    @Test
    @DisplayName(
            """
            GIVEN: ProductRequest, string category name and a auth obj 
            WHEN: create is called
            THEN: create a new product
            AND:  return dto 
                    """
    )
    void greenPath() {
        ProductDTO request = new ProductDTO(
                null,
                "Dish Soap",
                "wash stuff with it",
                "some_image",
                2,
                BigDecimal.valueOf(13.99),
                20,
                null
        );
        var user = createEntity();
        var category = new CategoryEntity("Cleaning");
        var auth = auth("test@gmail");

        when(userRepository.findByEmail("test@gmail")).thenReturn(Optional.of(user));
        when(categoryService.getByName(category.getName())).thenReturn(category);
        when(productRepository.save(any())).thenAnswer(invocation -> {
            ProductEntity entity =  invocation.getArgument(0);
            entity.setPublicId(UUID.randomUUID());
            return entity;
        });

        ProductDTO response = productService.create(request, category.getName(), auth);

        ArgumentCaptor<ProductEntity> captor = ArgumentCaptor.forClass(ProductEntity.class);

        verify(productRepository, times(1)).save(captor.capture());

        ProductEntity product = captor.getValue();

        //Entity
        assertEquals(user, product.getSeller());
        assertEquals(category, product.getCategory());
        assertEquals(BigDecimal.valueOf(11.19), product.getSpecialPrice());
        assertEquals("default.png", product.getImage());
        assertEquals(request.name(), product.getName());
        assertEquals(request.description(), product.getDescription());
        assertEquals(request.price(), product.getPrice());
        assertEquals(request.quantity(), product.getQuantity());
        //Response DTO
        assertEquals(request.name(), response.name());
        assertEquals(request.description(), response.description());
        assertEquals(request.price(), response.price());
        assertEquals(request.quantity(), response.quantity());
    }

    @Test
    @DisplayName(
            """
            GIVEN: ProductRequest, string category name and a auth obj 
            WHEN: create is called, logged in email is invalid
            THEN: throw exception
                    """
    )
    void redPath() {
        var auth = auth("test@gmail");

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.create(null, "", auth));

        verifyNoMoreInteractions(productRepository);
    }

    private UserEntity createEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("karl_jones");
        userEntity.setEmail("test@gmail");
        return userEntity;
    }

    private Authentication auth(String email) {
        var auth = mock(Authentication.class);
        var principal = mock(UserSecurity.class);

        when(auth.getPrincipal()).thenReturn(principal);
        when(principal.getUsername()).thenReturn(email);

        return auth;
    }
}
