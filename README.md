# Fingestio API 💰

API REST para gerenciamento financeiro pessoal construída com Spring Boot e Oracle Database.

## 🚀 Funcionalidades

- **Autenticação de usuários**
- **Gerenciamento de categorias** (receitas e despesas)
- **Gestão de cartões** (crédito, débito, pré-pago)
- **Controle de transações** com filtros avançados
- **Suporte a transações recorrentes**
- **API documentada** com padrão de resposta consistente

## 🛠️ Tecnologias

- **Java 21+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Oracle Database**
- **Maven**
- **Bean Validation**
- **CORS configurado**

## 📋 Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- Oracle Database (local ou remoto)
- Git

## ⚙️ Configuração do Ambiente

### 1. Clonar o repositório
```bash
git clone https://github.com/FIAP-FINGESTIO/api
cd fingestio-api
```

### 2. Configurar banco de dados

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

# Server Configuration
server.port=8080
```

### 3. Executar migrations (opcional)

Se você tiver um sistema de migrations configurado, execute:
```bash
# As migrations estão em src/main/resources/db/migrations/
# Execute-as manualmente no seu banco Oracle ou configure Flyway/Liquibase
```

## 🚀 Como executar

### Opção 1: Usando Maven diretamente
```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn spring-boot:run
```

### Opção 2: Gerando JAR e executando
```bash
# Compilar e gerar JAR
mvn clean package -DskipTests

# Executar JAR
java -jar target/fingestio-api-0.0.1-SNAPSHOT.jar
```

## 📊 Endpoints da API

### Autenticação
- `POST /auth/login` - Fazer login

### Categorias
- `GET /api/category/all/{user_id}` - Listar categorias do usuário
- `GET /category/{type}/{user_id}` - Listar categorias por tipo
- `POST /category/` - Criar categoria
- `PUT /category/{id}` - Atualizar categoria
- `DELETE /category/{id}` - Deletar categoria

### Cartões
- `GET /card/all/{userId}` - Listar cartões do usuário
- `POST /card/` - Criar cartão
- `PUT /card/{id}` - Atualizar cartão
- `DELETE /card/{id}` - Deletar cartão

### Transações
- `GET /transaction/{user_id}` - Listar transações do usuário
- `GET /transaction/search` - Buscar transações com filtros
- `POST /transaction/` - Criar transação
- `PUT /transaction/{id}` - Atualizar transação
- `DELETE /transaction/{id}` - Deletar transação

## 🧪 Exemplos de uso

### Fazer login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teste@teste.com",
    "password": "teste123"
  }'
```

### Criar categoria
```bash
curl -X POST http://localhost:8080/category/ \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alimentação",
    "description": "Gastos com comida",
    "type": 2,
    "userId": 1
  }'
```

### Criar transação
```bash
curl -X POST http://localhost:8080/transaction/ \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Compra no supermercado",
    "amount": 150.75,
    "currency": "BRL",
    "userId": 1,
    "categoryId": 5,
    "cardId": 3,
    "occurredAt": "15/11/2024",
    "isRecurring": false,
    "isPaid": true
  }'
```

### Buscar transações com filtros
```bash
curl "http://localhost:8080/transaction/search?userId=1&type=2&minAmount=100&maxAmount=1000&startDate=2024-01-01&endDate=2024-12-31"
```

## 🔒 Padrão de Resposta da API

### Resposta de Sucesso
```json
{
  "success": true,
  "message": "Operação realizada com sucesso",
  "data": {
    "id": 1,
    "name": "Exemplo"
  },
  "timestamp": "2024-11-05T10:30:00"
}
```

### Resposta de Erro
```json
{
  "success": false,
  "message": "Erro de validação nos dados enviados",
  "code": "VALIDATION_ERROR",
  "errors": {
    "email": "Email deve ter um formato válido",
    "password": "Senha deve ter pelo menos 6 caracteres"
  },
  "timestamp": "2024-11-05T10:30:00"
}
```

## 🌐 Deploy em Produção

### Variáveis de Ambiente
```bash
export DB_URL=jdbc:oracle:thin:@seu-servidor:1521:xe
export DB_USERNAME=usuario_prod
export DB_PASSWORD=senha_prod
export SERVER_PORT=8080
export SPRING_PROFILES_ACTIVE=prod
```

## 🔧 Troubleshooting

### Problemas comuns

1. **Erro de conexão com banco**
   - Verifique se o Oracle está rodando
   - Confirme as credenciais no application.properties

2. **Porta já em uso**
   - Altere a porta no application.properties: `server.port=8081`
   - Ou mate o processo: `lsof -ti:8080 | xargs kill -9`

3. **Erro de compilação**
   - Verifique se tem Java 21+: `java -version`
   - Limpe o cache do Maven: `mvn clean`

## 📚 Documentação Adicional

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Oracle JDBC Documentation](https://docs.oracle.com/en/database/oracle/oracle-database/19/jjdbc/)
- [Maven Documentation](https://maven.apache.org/guides/)

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

