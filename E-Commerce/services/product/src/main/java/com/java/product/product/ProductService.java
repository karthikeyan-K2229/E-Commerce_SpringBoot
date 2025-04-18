package com.java.product.product;

import com.java.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  ProductMapper mapper;
    public Integer createProduct(ProductRequest request) {
        var product=mapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(mapper::toProductresponse).
                orElseThrow(()-> new EntityNotFoundException("Product not found with ID:: " + productId));

    }

    public List<ProductResponse> findAll() {
        return  productRepository.findAll()
                .stream().map(mapper::toProductresponse)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds=request.stream().map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts=productRepository.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProducts.size())
        {
            throw new ProductPurchaseException("One or more products does not exist");

        }
        var sortedRequest=request.
                stream().
                sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();

        var purchasedProducts=new ArrayList<ProductPurchaseResponse>();
        for(int i=0;i<storedProducts.size();i++)
        {
            var product=storedProducts.get(i);
            var productRequest=sortedRequest.get(i);
            if(product.getAvailableQuantity()<productRequest.quantity())
            {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity=product.getAvailableQuantity()-productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }
        return purchasedProducts;
    }
}
