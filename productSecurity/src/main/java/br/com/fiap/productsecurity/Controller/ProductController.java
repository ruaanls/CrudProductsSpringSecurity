package br.com.fiap.productsecurity.Controller;

import br.com.fiap.productsecurity.DTO.ProductRequestDTO;
import br.com.fiap.productsecurity.DTO.ProductResponseDto;
import br.com.fiap.productsecurity.Mapper.ProductMapper;
import br.com.fiap.productsecurity.model.Product;
import br.com.fiap.productsecurity.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDTO request)
    {
        Product product = productMapper.requestToProduct(request);
        ProductResponseDto productCreated = productService.createProduct(product);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable Long id)
    {
        Product product = productService.findProductById(id);
         ProductResponseDto responseDto = productMapper.productToResponse(product);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO request)
    {
        ProductResponseDto productUpdated = productService.updateProduct(id, request);
        return new ResponseEntity<>(productUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
