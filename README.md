# Testes Automatizados com JUnit

Este repositório foi criado com o propósito de demonstrar na prática a implementação de **Testes Automatizados e Unitários** utilizando a linguagem Java e o framework JUnit 5. Ele serve como um guia prático e portfólio para desenvolvimento focado em engenharia de software e garantia de qualidade.

A estrutura e os exemplos aqui presentes simulam cenários reais de regras de negócio, indo de validações simples até lógicas complexas de descontos em um ambiente de *e-commerce*, preparando o terreno para integrações em pipelines de CI/CD.

## Estrutura do Projeto

O projeto segue a arquitetura padrão de diretórios do **Maven**, garantindo a separação estrita entre o código de produção e as suítes de teste:

- `src/main/java/`: Contém as classes de produção com a lógica de negócio.
    - `Item`: Entidade que representa um item de compra no carrinho.
    - `CalculadoraDeDescontos`: Demonstração de lógica matemática pura para aplicação de regras simples de desconto.
    - `CheckoutService`: Serviço que orquestra lógicas mais complexas, incluindo descontos cumulativos, *cap* máximo (limite) e validação de cupons.

- `src/test/java/`: Contém as suítes de testes unitários.
    - `CalculadoraDeDescontosTest`: Cobre validações básicas, fluxos felizes (compras normais) e fluxos de exceção (bloqueio de preços negativos).
    - `CheckoutServiceTest`: Suíte avançada que utiliza recursos sofisticados do JUnit para escalar a cobertura de testes sem repetição de código.

## Conceitos de Teste Abordados

Ao explorar o código-fonte de teste (`src/test`), você encontrará a aplicação direta dos seguintes conceitos de engenharia:

* **Asserções e Exceções:** Uso estratégico de `assertEquals` com definição de *delta* (para precisão de ponto flutuante) e `assertThrows` para validar o comportamento do sistema diante de dados inválidos.
* **Testes Parametrizados (`@ParameterizedTest` / `@CsvSource`):** Aplicação de **Análise de Valor Limite (Boundary Value Analysis)**, executando múltiplas combinações de dados injetados em uma única função e testando os exatos limiares das regras de negócio.
* **Organização Semântica (`@Nested` / `@DisplayName`):** Estruturação das classes de teste em sub-árvores lógicas. Isso facilita a leitura dos logs de execução no terminal, permitindo rápida identificação de falhas por domínio (ex: Falha em Regra de Cupom vs. Regra de VIP).
* **Isolamento de Estado (`@BeforeEach`):** Prevenção rigorosa de vazamento de estado (*state leakage*) entre os testes, garantindo que a execução de um cenário não afete o resultado do subsequente.

## Como Executar os Testes

### Pré-requisitos
* **Java JDK**: Versão 11 ou superior (preferencialmente 17 LTS ou 21).
* **Maven**: Versão 3.6+
* **IDE**: IntelliJ IDEA (recomendado).

### Via Terminal (Linha de Comando)
A execução via terminal é ideal para validar se o projeto está pronto para integração contínua (CI/CD). Na raiz do projeto (onde está localizado o `pom.xml`), execute:

```bash
# Limpa builds anteriores e roda toda a suíte de testes
mvn clean test