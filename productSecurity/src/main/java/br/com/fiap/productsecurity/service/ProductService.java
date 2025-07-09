package br.com.fiap.productsecurity.service;

import br.com.fiap.productsecurity.DTO.ProductRequestDTO;
import br.com.fiap.productsecurity.DTO.ProductResponseDto;
import br.com.fiap.productsecurity.Exception.Custom.OracleInputException;
import br.com.fiap.productsecurity.Mapper.ProductMapper;
import br.com.fiap.productsecurity.model.Product;
import br.com.fiap.productsecurity.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            Product savedProduct = productsRepository.save(product);
            return productMapper.productToResponse(savedProduct);
        } catch (DataIntegrityViolationException e) {
            throw new OracleInputException("Erro ao salvar produto: " + e.getMostSpecificCause().getMessage());
        }
    }

    public Product findProductById(Long id)
    {
        try {
            return productsRepository.findById(id)
                .orElseThrow(() -> new OracleInputException("Produto não encontrado com ID: " + id));
        } catch (Exception e) {
            throw new OracleInputException("Erro ao buscar produto: " + e.getMessage());
        }
    }

    public void deleteProduct(Long id)
    {
        try {
            productsRepository.deleteById(id);
        } catch (Exception e) {
            throw new OracleInputException("Erro ao deletar produto: " + e.getMessage());
        }
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDTO request)
    {
        try {
            Product product = productsRepository.findById(id)
                .orElseThrow(() -> new OracleInputException("Produto não encontrado com ID: " + id));
            
            product.setName(request.getName());
            product.setPrice(request.getPrice());
            
            Product savedProduct = productsRepository.save(product);
            return productMapper.productToResponse(savedProduct);
        } catch (DataIntegrityViolationException e) {
            throw new OracleInputException("Erro ao atualizar produto: " + e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            throw new OracleInputException("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public List<Product> listAllProducts()
    {
        try {
            return productsRepository.findAll();
        } catch (Exception e) {
            throw new OracleInputException("Erro ao listar produtos: " + e.getMessage());
        }
    }
}
