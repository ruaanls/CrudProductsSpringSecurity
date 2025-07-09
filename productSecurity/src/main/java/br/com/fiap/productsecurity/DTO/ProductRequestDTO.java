package br.com.fiap.productsecurity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO
{
    @NotNull(message = "O nome do produto não pode ser nulo")
    @NotBlank(message = "O nome do produto não pode estar em branco")
    private String name;

    @NotNull(message = "O preço do produto não pode ser nulo")
    @Positive(message = "O preço do produto deve ser maior que zero")
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
