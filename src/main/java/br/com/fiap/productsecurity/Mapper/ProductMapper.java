package br.com.fiap.productsecurity.Mapper;

import br.com.fiap.productsecurity.DTO.ProductRequestDTO;
import br.com.fiap.productsecurity.DTO.ProductResponseDto;
import br.com.fiap.productsecurity.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper
{
    public Product requestToProduct(ProductRequestDTO request)
    {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        return product;
    }

    public ProductResponseDto productToResponse(Product product)
    {
        ProductResponseDto response = new ProductResponseDto();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        return response;
    }
}
