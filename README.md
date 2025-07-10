# Product Security API

API REST desenvolvida com Spring Boot e Spring Security para gerenciamento de produtos com autenticação e autorização.

## 🛠 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Security
- JWT (JSON Web Token)
- Oracle Database
- Gradle

## 📋 Pré-requisitos

- Java 17 ou superior
- Oracle Database
- Gradle
- Git

## 🚀 Como executar o projeto

### Clonando o repositório

```bash
git clone <url-do-seu-repositorio>
cd productSecurity
```

### Configurando o banco de dados

1. Configure as variáveis de ambiente no seu sistema:
   - `DB_USERNAME`: Usuário do banco Oracle
   - `DB_PASSWORD`: Senha do banco Oracle
   - `JWT_SECRET`: Chave secreta para geração dos tokens JWT (opcional, valor padrão: "my-secret-key")

2. O banco Oracle já deve estar configurado em:
   ```
   URL: oracle.fiap.com.br:1521/ORCL
   ```

### Executando a aplicação

Rode o arquivo ProductSecurityApplication ou faça esse comando
```bash
./gradlew bootRun
```
Link do vídeo de demonstração da aplicação funcionando em: https://www.youtube.com/watch?v=uMTtzVB3zN8 

A aplicação estará disponível pelo IP de sua máquina utilizando o conteiner docker através do dockerfile.   
ou localmente em `http://localhost:8080`

## 🔐 Endpoints da API

### Autenticação

#### Registro de Usuário
```http
POST /auth/register
```
Body:
```json
{
    "login": "seu_usuario",
    "password": "sua_senha",
    "user_role": "ROLE_USER"  // ou "ROLE_ADMIN" para privilégios administrativos
}
```
Resposta de sucesso: `200 OK`

#### Login
```http
POST /auth/login
```
Body:
```json
{
    "login": "seu_usuario",
    "password": "sua_senha"
}
```
Resposta de sucesso:
```json
{
    "token": "seu_token_jwt"
}
```

### Produtos

⚠️ **Importante**: Para todas as requisições de produtos, inclua o token JWT no header:
```
Authorization: Bearer seu_token_jwt
```

#### Criar Produto (Requer ROLE_ADMIN)
```http
POST /product/create
```
Body:
```json
{
    "name": "Nome do Produto",
    "price": 99.99
}
```

#### Buscar Produto por ID
```http
GET /product/{id}
```

#### Atualizar Produto (Requer ROLE_ADMIN)
```http
PUT /product/{id}
```
Body:
```json
{
    "name": "Novo Nome",
    "price": 149.99
}
```

#### Deletar Produto (Requer ROLE_ADMIN)
```http
DELETE /product/{id}
```

## 🚨 Tratamento de Erros

A API retorna as seguintes respostas em caso de erro:

### Erros de Autenticação
```json
{
    "status": "UNAUTHORIZED",
    "message": "Usuário ou senha inválidos"
}
```

### Erros de Autorização
```json
{
    "status": "FORBIDDEN",
    "message": "Acesso negado: Você não tem permissão para acessar este recurso"
}
```

### Erros de Validação
```json
{
    "status": "BAD_REQUEST",
    "message": "Erro de validação",
    "errors": [
        "O nome do produto não pode estar em branco",
        "O preço do produto deve ser maior que zero"
    ]
}
```

### Erros de Banco de Dados
```json
{
    "status": "INTERNAL_SERVER_ERROR",
    "message": "Erro na inserção dos dados, por favor tente novamente mais tarde!"
}
```

## 🔒 Segurança

- Todas as senhas são criptografadas antes de serem armazenadas
- Tokens JWT com expiração
- Validação de roles para endpoints administrativos
- Mensagens de erro genéricas para evitar exposição de dados sensíveis

## 👥 Roles e Permissões

### ROLE_USER
- Pode visualizar produtos

### ROLE_ADMIN
- Pode criar produtos
- Pode atualizar produtos
- Pode deletar produtos
- Pode visualizar produtos

## ⚠️ Observações Importantes

1. Mantenha suas credenciais de banco de dados seguras
2. O token JWT tem prazo de expiração, faça novo login quando expirar
3. Certifique-se de usar HTTPS em produção
4. Sempre use o prefixo "Bearer " antes do token JWT nos headers

## 🐛 Problemas Comuns

1. **Erro de conexão com banco de dados**
   - Verifique se as variáveis de ambiente estão configuradas corretamente
   - Confirme se o banco Oracle está acessível

2. **Token expirado**
   - Faça login novamente para obter um novo token

3. **Acesso negado**
   - Verifique se está usando o token correto
   - Confirme se sua role tem permissão para a operação

## 📝 Licença

Este projeto está sob a licença [MIT](LICENSE). 