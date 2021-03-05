# Demo-CI: Demonstração Prática do Uso de Servidores de Integração Contínua

Este repositório descreve um roteiro prático para configuração e uso de um **Servidor de Integração Contínua**. O objetivo é proporcionar ao aluno um primeiro contato prático com integração contínua.

Se você ainda não sabe o que é **Integração Contínua** e qual o papel desempenhado por servidores de CI, recomendamos ler o [Capítulo 10](https://engsoftmoderna.info/cap10.html) do nosso livro texto ([Engenharia de Software Moderna](https://engsoftmoderna.info/cap10.html)).

Apesar de existirem diversos servidores de integração contínua, nesta demonstração iremos usar um recurso nativo do GitHub, chamado **GitHub Actions**, para configurar um servidor de CI. 

<p align="center">
    <img width="70%" src="https://user-images.githubusercontent.com/7620947/109080916-232f8200-76e0-11eb-8d02-9ca9f518cea2.png" />
</p>

O Github Actions permite executar programas externos assim que determinados eventos forem detectados em um repositório GitHub. Como nosso intuito é configurar um servidor CI, iremos usar o GitHub Actions para compilar todo o código (*build*) do projeto e rodar seus testes de unidade quando um Pull Request (PR) for aberto no projeto, conforme ilustrado a seguir.

<p align="center">
    <img width="70%" src="https://user-images.githubusercontent.com/7620947/110053018-50f77500-7d37-11eb-9ca1-3de609b93584.png" />
</p>

## Programa de Exemplo

Para realizar a demonstração, vamos usar um programa Java muito simples, que já foi criado e está disponível neste repositório ([Calculadora.java](https://github.com/aserg-ufmg/demo-ci/blob/main/src/main/java/br/ufmg/dcc/Calculadora.java)):

```java
public class Calculadora {

  public int soma(int x, int y) {
    return x + y;
  }

  public int subtrai(int x, int y) {
    return x - y;
  }
}
```

Quando chegar um PR no repositório, o servidor de CI vai realizar um *build* desse programa e rodar o seguinte teste de unidade (também já disponível no repositório, veja em [CalculadoraTest](https://github.com/aserg-ufmg/demo-ci/blob/main/src/test/java/br/ufmg/dcc/CalculadoraTest.java)):

```java
public class CalculadoraTest {
  @Test
  public void testeSoma1() {
    Calculadora calc = new Calculadora();
    int resultadoEsperado = 5;
    int resultadoRetornado = calc.soma(2,3);
    assertEquals(resultadoEsperado, resultadoRetornado);
  }

  @Test
  public void testeSoma2() {
    Calculadora calc = new Calculadora();
    assertEquals(10, calc.soma(4,6));
  }
}
```

## Tarefa #1: Configuranr o GitHub Actions

Antes de mais nada realize um fork deste repositório. Para isso, basta clicar no botão **Fork** no canto superior direito desta página.

Ou seja, você irá configurar um servidor de CI nesta sua cópia do repo.

A etapa seguinte pode ser aplicada através do editor de arquivos do próprio GitHub. Porém, caso você tenha interesse em criar o arquivo localmente em sua máquina, basta cloná-lo através do comando, onde `<USER>` deve ser substituído pelo seu usuário no GitHub, isto é, o usuário na qual o fork foi realizado:

```bash
git clone https://github.com/<USER>/demo-ci.git
```

Em seguida, copie o código a seguir para um arquivo na raiz do projeto que deve ter o seguinte nome: `.github/workflows/actions.yaml`.

Isto é, crie diretórios `.github` e depois `workflows` e salve o código abaixo no arquivo `actions.yaml`.

```yaml
name: Github CI
on: [push,pull_request] # Evento que irá iniciar a tarefa

jobs:
  pipeline:
    runs-on: ubuntu-latest # Os comandos serão executados em um sistema operacional Linux

    steps:
      - name: Git Checkout
        uses: actions/checkout@v2 # Faz o checkout do código recebido

      - name: Set up JDK 1.8
        uses: actions/setup-java@v1 # Configura o Java 1.8
        with:
          java-version: 1.8

      - name: Build
        run: mvn package -Dmaven.test.skip=true # Compila o código fonte

      - name: Unit Test
        run: mvn test # Executada os testes de unidade
```

Esse arquivo ativa e configura o GitHub Actions para -- toda vez que ocorrer um evento `push` ou `pull_request` -- realizar três tarefas: (1) realizar o checkout do código; (2) realizar um build; (3) rodar os testes de unidade.

Lembre-se, após criar o arquivo, você deverá criar um `commit` com as mudanças realizadas e enviá-las para o seu repositório atrávés do `git push`:

```bash
git add --all
git commit -m "Configurando GitHub Actions"
git push origin main
```

Após criar o arquivo, o GitHub Actions iniciará automaticamente o fluxo de tarefas e você pode acompanhar todo o processo através da aba Actions do seu repositório.

<p align="center">
    <img width="50%" src="https://user-images.githubusercontent.com/7620947/110059807-b8b3bd00-7d43-11eb-9e57-e6ba1fa3457a.png" />
</p>

## Tarefa #2: Criando um PR com bug?


Para finalizar, você deve criar um PR com um pequeno bug, para verificar se o nosso código será barrado durante o processo de integração. Desta forma, você deve alterar a função `soma` no arquivo [src/main/java/br/ufmg/dcc/Calculadora.java](https://github.com/rodrigo-brito/roteiro-github-actions/blob/main/src/main/java/br/ufmg/dcc/Calculadora.java). Você deve alterar a linha 6, alterando o cálculo de soma para `x + y + 1`, como apresentado abaixo. Logo, os testes devem falhar após a alteração do código.

```diff
--- a/src/main/java/br/ufmg/dcc/Calculadora.java
+++ b/src/main/java/br/ufmg/dcc/Calculadora.java
@@ -3,7 +3,7 @@ package br.ufmg.dcc;
 public class Calculadora {

   public int soma(int x, int y) {
-    return x + y;
+    return x + y + 1;
   }

   public int subtrai(int x, int y) {
```

Após modificar o código, você deve criar uma nova branch, comitar as mudanças e dar um push para seu repositório.

```bash
git checkout -b bug
git add --all
git commit -m "Incluindo alterações na função soma"
git push origin bug
```

Após executar os comandos descritos anteriormente, você deve abrir o link apresentado no terminal para criar um Pull Request do branch `bug` para o branch `main`

<p align="center">
    <img width="70%" src="https://user-images.githubusercontent.com/7620947/110060105-2364f880-7d44-11eb-9dff-1bde0c553d9d.png" />
</p>

Após clicar no link, você deve selecionar o branch de origem e destino do pull request. Desta forma, você deve selecionar como origem o branch `bug` e destino o branch `main`, assim como apresentado abaixo. É importante ressaltar que o Pull Request está sendo criado no seu repositório:

<p align="center">
    <img width="70%" src="https://user-images.githubusercontent.com/7620947/110060765-2dd3c200-7d45-11eb-9a19-1a53d5dd24c6.png" />
</p>

Após confirmar o Pull Request, o fluxo de trabalho iniciará e você poderá acompanhar o processo através da aba Actions.
Se tudo ocorrer como planejado, o seu pull request indicará a falha, impedindo a integraço do código, como mostrado abaixo.

<p align="center">
    <img width="70%" src="https://user-images.githubusercontent.com/7620947/110062278-bc494300-7d47-11eb-9d80-c5642942d346.png" />
</p>
