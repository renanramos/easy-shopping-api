# Easy Shopping (API)

Aplicação backend desenvolvida como trabalho de conclusão de curso da PUC Minas, referente ao curso

**Desenvolvimento Web Full Stack**.

Página inicial: [easy-shopping-api](https://renanramos.github.io/easy-shopping-api/quality/index.html)

---


# **Arquitetura da aplicação - Clean Architecture**

## 📌 **Fluxo de Dados entre as Camadas**

Este projeto segue os princípios do **Clean Architecture**, garantindo separação de responsabilidades e fácil
manutenção. O fluxo de dados ocorre da seguinte forma:

### **1️⃣ Repositório (`Repository`)**

- Responsável por acessar o banco de dados.
- Retorna a entidade `AddressEntity`, que representa a tabela no banco.
- **Localização:** `interfaceadapter.repository`

### **2️⃣ Gateway (`Gateway`)**

- Atua como intermediário entre a persistência e o domínio.
- **Solicita** o mapeamento de `AddressEntity` para `AddressDomain` via **MapStruct**.
- **Localização (interface):** `br.com.renanrramos.easyshopping.core.gateway`
- **Localização (implementação):** `br.com.renanrramos.easyshopping.interfaceadapter.gateway`

### **3️⃣ Caso de Uso (`UseCase`)**

- Contém a regra de negócio.
- Realiza a conversão de `AddressDomain` para `AddressDTO`.
- **Localização:** `br.com.renanrramos.easyshopping.core.usecase`

### **4️⃣ Interface Adapter (`Mapper`)**

- Responsável pelo mapeamento de entidades.
- Implementado utilizando **MapStruct**.
- **Localização:** `interfaceadapter.mapper`

## 🛠 **Exemplo de Implementação**

### **Repository**

```java
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
```

### **Mapper (`Interface Adapter`)**

```java

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDomain toDomain(AddressEntity entity);

    AddressDTO toDTO(AddressDomain domain);
}
```

### **Gateway**

```java

@RequiredArgsConstructor
public class AddressGateway {
    private final AddressRepository repository;
    private final AddressMapper mapper;

    public AddressDomain findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
    }
}
```

### **UseCase**

```java

@RequiredArgsConstructor
public class AddressUseCase {
    private final AddressGateway gateway;
    private final AddressMapper mapper;

    public AddressDTO findAddress(Long id) {
        return mapper.toDTO(gateway.findById(id));
    }
}
