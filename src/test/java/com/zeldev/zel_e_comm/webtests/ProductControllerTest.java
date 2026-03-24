package com.zeldev.zel_e_comm.webtests;

import com.zeldev.zel_e_comm.controller.ProductController;
import com.zeldev.zel_e_comm.domain.TokenData;
import com.zeldev.zel_e_comm.entity.RoleEntity;
import com.zeldev.zel_e_comm.entity.UserEntity;
import com.zeldev.zel_e_comm.enumeration.RoleType;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.JwtService;
import com.zeldev.zel_e_comm.service.ProductOrchestrationService;
import com.zeldev.zel_e_comm.service.ProductService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.Optional;
import java.util.Set;

import static com.zeldev.zel_e_comm.enumeration.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @MockitoBean
    private ProductService productService;
    @MockitoBean
    private ProductOrchestrationService orchestrationService;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private MockMvcTester mvc;

    @Test
    void greenPath() {
        var user = new UserEntity();
        user.setId(1L);
        user.setTokenVersion(0);
        user.setStatus(ACTIVE);
        user.setUsername("zel");
        user.setEmail("zel@gmail");
        user.setRoles(Set.of(new RoleEntity(RoleType.USER)));
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);

                when(jwtService.getJwtFromCookie(any()))
                        .thenReturn("my-token");

        when(jwtService.getTokenData("my-token"))
                .thenReturn(TokenData.builder()
                        .valid(true)
                        .userId(1L)
                        .username("test")
                        .tokenVersion(0)
                        .build());
        when(userRepository.findByIdWithRoles(1L)).thenReturn(Optional.of(user));

        var response = mvc.get().uri("/api/v1/products").cookie(new Cookie("access_token_z", "my-token")).exchange();

        assertThat(response).hasStatus(HttpStatus.OK);
    }
}
