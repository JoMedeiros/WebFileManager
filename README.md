# WebFileManager

Uma API REST para gerenciar arquivos.

## Dependências

- Java 8
- Maven

## Como executar

Para compilar:

```sh
mvn clean compile
```

Para executar:

```sh 
mvn spring-boot:run 
```

Irá exeucutar em `localhost:8080`.
Você pode testar acessando o endpoint:

```sh
curl -X GET "http://localhost:8080/api/list" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"dirPath": "/caminho/do/diretorio"}'
```

Você também pode acessar a documentação em `http://localhost:8080/swagger-ui.html`

### Funcionalidades
Listar arquivos, mover, deletar, criar pastas, etc.

