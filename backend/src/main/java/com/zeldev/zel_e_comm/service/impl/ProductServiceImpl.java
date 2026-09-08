package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.config.AppConfig;
import com.zeldev.zel_e_comm.dto.request.ProductRequest;
import com.zeldev.zel_e_comm.dto.response.PageResponse;
import com.zeldev.zel_e_comm.dto.response.ProductResponse;
import com.zeldev.zel_e_comm.entity.CategoryEntity;
import com.zeldev.zel_e_comm.entity.ProductEntity;
import com.zeldev.zel_e_comm.exception.APIException;
import com.zeldev.zel_e_comm.exception.InsufficientStockException;
import com.zeldev.zel_e_comm.exception.ResourceNotFoundException;
import com.zeldev.zel_e_comm.exception.UserNotFoundException;
import com.zeldev.zel_e_comm.repository.ProductRepository;
import com.zeldev.zel_e_comm.repository.UserRepository;
import com.zeldev.zel_e_comm.service.CategoryService;
import com.zeldev.zel_e_comm.service.FileService;
import com.zeldev.zel_e_comm.service.ProductService;
import com.zeldev.zel_e_comm.util.AuthUtils;
import com.zeldev.zel_e_comm.util.ProductUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.zeldev.zel_e_comm.util.PageUtils.getPageable;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final ProductUtils productUtils;
    private final AuthUtils authUtils;
    private final AppConfig appConfig;

    @Override
    public ProductResponse create(ProductRequest request, String categoryName) {
        var user = userRepository.findByEmail(authUtils.getLoggedInEmail()).orElseThrow(() -> new UserNotFoundException("user not found"));
        ProductEntity entity = productUtils.buildProductEntity(request);
        entity.setSeller(user);
        CategoryEntity category = categoryService.getByName(categoryName);
        entity.setCategory(category);
        entity.setSpecialPrice(entity.calculateSpecialPrice());
        entity.setImage("default.png");
        //.save is called by the SimpleJpaRepository impl, if it's a new entity calls entityManager.persist else calls .merge. It evaluates the id field
        //if id is null then considers as a new entity
        ProductEntity saved = productRepository.save(entity);
        return productUtils.toProductResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getAllProducts(Integer page, Integer size, String sortBy, String sortOrder, String category, String keyword) {
        Pageable pageDetails = getPageable(page, size, sortBy, sortOrder);

        Specification<ProductEntity> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (keyword != null && !keyword.isBlank()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%")
                    );
        }

        if (category != null && !category.isBlank()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("name"), category)
            );
        }

        Page<ProductEntity> productPage = productRepository.findAll(spec, pageDetails);

        List<ProductEntity> products = productPage.getContent();
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return productUtils.buildProductPageResponse(productPage, products);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getProductsByCategory(String id, Integer page, Integer size, String sortBy, String sortOrder) {
        Pageable pageDetails = getPageable(page, size, sortBy, sortOrder);
        Page<ProductEntity> productPage = productRepository.findByCategory_IdOrderByPriceAsc(categoryService.getByName(id).getId(), pageDetails);
        List<ProductEntity> products = productPage.getContent();
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return productUtils.buildProductPageResponse(productPage, products);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getProductsByKeyword(String keyword, Integer page, Integer size, String sortBy, String sortOrder) {
        Pageable pageDetails = getPageable(page, size, sortBy, sortOrder);
        Page<ProductEntity> productPage = productRepository.findByNameLikeIgnoreCase("%" + keyword + "%", pageDetails);
        List<ProductEntity> products = productPage.getContent();
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return productUtils.buildProductPageResponse(productPage, products);
    }

    @Override
    public PageResponse<ProductResponse> getProductsBySeller(Integer page, Integer size, String sortBy, String sortOrder) {
        Pageable pageDetails = getPageable(page, size, sortBy, sortOrder);
        Page<ProductEntity> productPage = productRepository.findBySellerEmail(authUtils.getLoggedInEmail(), pageDetails);
        List<ProductEntity> products = productPage.getContent();
        if (products.isEmpty()) throw new APIException("No products have been added yet :(");
        return productUtils.buildProductPageResponse(productPage, products);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productDTO, String productId) {
        ProductEntity productDB = findByPublicId(productId);
        if (productDTO.productName() != null && !productDTO.productName().isBlank()) {
            productDB.setName(productDTO.productName());
        }
        if (productDTO.description() != null && !productDTO.description().isBlank()) {
            productDB.setDescription(productDTO.description());
        }

        boolean priceChanged = false;
        boolean discountChanged = false;

        if (productDTO.price() != null) {
            productDB.setPrice(productDTO.price());
            priceChanged = true;
        }
        if (productDTO.discount() != null) {
            productDB.setDiscount(productDTO.discount());
            discountChanged = true;
        }

        if (priceChanged || discountChanged) {
            productDB.setSpecialPrice(productDB.calculateSpecialPrice());
        }

        if (productDTO.quantity() != null) {
            productDB.setQuantity(productDTO.quantity());
        }

        return productUtils.toProductResponse(productDB);
    }

    @Override
    public ProductResponse deleteProduct(String productId) {
        ProductEntity productDB = findByPublicId(productId);

        productRepository.delete(productDB);

        return productUtils.toProductResponse(productDB);
    }

    @Override
    public void validateQuantity(Integer requestedQuantity, UUID productId) {
        ProductEntity product = getByPublicId(productId);
        Integer inStock = product.getQuantity();
        if (inStock == 0 || requestedQuantity > inStock) throw new InsufficientStockException("The requested quantity is not available");
    }

    @Override
    public ProductResponse updateImage(String productId, MultipartFile image) throws IOException {
        ProductEntity productDB = findByPublicId(productId);
        String filename = fileService.uploadImage(appConfig.getImages(), image);
        productDB.setImage(filename);
        return productUtils.toProductResponse(productDB);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductEntity findByPublicId(String publicId) {
        return productRepository.findByPublicId(UUID.fromString(publicId)).orElseThrow(() -> new ResourceNotFoundException(publicId, "Product"));
    }

    @Override
    public void decreaseStock(UUID productId, Integer requestedQuantity) {
        ProductEntity product = getByPublicId(productId);
        if (product.getQuantity() < requestedQuantity) throw new InsufficientStockException(String.format("Insufficient stock for product %s", product.getName()));
        product.setQuantity(product.getQuantity() - requestedQuantity);
    }

    private ProductEntity getByPublicId(UUID productId) {
        return productRepository.findByPublicId(productId).orElseThrow(() -> new ResourceNotFoundException(productId.toString(), "Product"));
    }
}
