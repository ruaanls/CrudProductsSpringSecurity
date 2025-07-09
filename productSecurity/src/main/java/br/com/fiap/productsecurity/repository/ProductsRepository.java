package br.com.fiap.productsecurity.repository;

import br.com.fiap.productsecurity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
}
