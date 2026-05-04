package com.zeldev.zel_e_comm.unittests.cartservice;

import com.zeldev.zel_e_comm.entity.CartEntity;
import com.zeldev.zel_e_comm.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetCartsTest extends CartServiceBaseTest{

    @Test
    @DisplayName(
            """
                    WHEN: getCarts is called
                    THEN: returns a list of CartDTOs
                    """
    )
    void greenPath() {
        List<CartEntity> carts = List.of(new CartEntity(), new CartEntity());

        when(cartRepository.findAll()).thenReturn(carts);

        var response = cartService.getCarts();

        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName(
            """
                    WHEN: getCarts is called
                    THEN: no carts in db
                    AND: throws exception
                    """
    )
    void redPath() {
        when(cartRepository.findAll()).thenReturn(List.of());

        assertThrows(APIException.class, () -> cartService.getCarts());
    }
}
