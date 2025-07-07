package br.com.fiap.productsecurity.Exception.Custom;

public class RegisterFailedException extends RuntimeException
{
    public RegisterFailedException(String message) {
        super(message);
    }

    public RegisterFailedException() {
        super("Erro no registro, usuário já cadastrado");
    }
}
