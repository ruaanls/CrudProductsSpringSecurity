package br.com.fiap.productsecurity.service;

import br.com.fiap.productsecurity.DTO.ProductRequestDTO;
import br.com.fiap.productsecurity.DTO.ProductResponseDto;
import br.com.fiap.productsecurity.Mapper.ProductMapper;
import br.com.fiap.productsecurity.model.Product;
import br.com.fiap.productsecurity.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService
{
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductsRepository productsRepository;

    public ProductResponseDto createProduct(Product product)
    {
        Product savedProduct = productsRepository.save(product);
        return productMapper.productToResponse(savedProduct);
    }

    public Product findProductById(Long id)
    {
        return productsRepository.findById(id).orElseThrow();
    }
    public void deleteProduct(Long id)
    {
        productsRepository.deleteById(id);
    }
    public ProductResponseDto updateProduct(Long id, ProductRequestDTO request)
    {
        Product product = productsRepository.findById(id).orElseThrow();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        Product savedProduct = productsRepository.save(product);
        return productMapper.productToResponse(savedProduct);
    }

    public List<Product> listAllProducts()
    {
        return productsRepository.findAll();
    }
}
