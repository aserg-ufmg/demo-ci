# Demo-CI: Demonstração Prática do Uso de Servidores de Integração Contínua

Este repositório apresenta uma demonstração prática da configuração e uso de um **Servidor de Integração Contínua**. Se você ainda não sabe o que é **Integração Contínua** e qual o papel desempenhado por servidores de CI, recomendamos ler o [Capítulo 10](https://engsoftmoderna.info/cap10.html) do nosso livro texto ([Engenharia de Software Moderna](https://engsoftmoderna.info/cap10.html)).

Apesar de existirem diversos servidores de integração contínua, nesta demonstração iremos usar um recurso nativo do GitHub, chamado **GitHub Actions**, para configurar um servidor de CI. 

O Github Actions permite executar alguns programas externos assim que determinados eventos forem detectados em um repositório GitHub. Como nosso intuito é configurar um servidor CI, iremos usar o GitHub Actions para rodar compilar todo o código do projeto e rodar seus testes de unidade sempre que um novo Pull Request (PR) for aberto no projeto, conforme ilustrado a seguir.

<p align="center">
    <img width="70%" src="https://user-images.githubusercontent.com/7620947/109251864-fefc9f80-77ca-11eb-862f-3e016a6414af.png" />
</p>


<p align="center">
    <img width="100%" src="https://user-images.githubusercontent.com/7620947/109080916-232f8200-76e0-11eb-8d02-9ca9f518cea2.png" />
</p>

 Git Push, abertura de issues, entre outras coisas. Desta forma o usuário pode definir tarefas e automatizar execução de teste de unidade, linters e distribuição da aplicação após integrada.

Neste roteiro, você irá aprender a configurar um simples fluxo de integração contínua para uma aplicaçãoo desenvolvida em Java. A partir de um Pull Request ou Push, o GitHub Actions vai executar os testes de unidade e compilar a nossa aplicação


Para isso, nós precisamos criar o arquivo `.github/workflows/actions.yaml`, ele será responsável por definir as instruções do nosso fluxo de tarefas. O conteúdo do arquivo será definido da seguinte forma:

```yaml
name: Github CI
on: [pull_request] # Evento que irá iniciar a tarefa

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

      - name: Unit Test
        run: mvn test # Executada os testes de unidade

      - name: Build
        run: mvn package -Dmaven.test.skip=true # Compila o código fonte
```

Após criar o arquivo, o GitHub Actions iniciará automaticamente o fluxo de tarefass e você pode acompanhar todo o processo através da aba Actions do seu repositório

<p align="center">
    <img width="70%" src="https://user-images.githubusercontent.com/7620947/109092561-8677df00-76f5-11eb-9db1-b2409505b721.png" />
</p>

Para finalizar, nós iremos criar um pull request com um pequeno bug, para verificar se o nosso código será barrado durante o processo de integração. Desta forma, você deve alterar a função `soma` no arquivo [src/main/java/br/ufmg/dcc/Calculadora.java](https://github.com/rodrigo-brito/roteiro-github-actions/blob/main/src/main/java/br/ufmg/dcc/Calculadora.java). Você deve alterar a linha 6, alterando o cálculo de soma para `x + y + 1`, como apresentado abaixo. Logo, os testes devem falhar após a alteração do código.

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

Após executar os comandos descritos anteriormente, você deve abrir o link exibido no terminal para criar um Pull Request do branch `bug` para o branch `main`

<p align="center">
    <img width="70%" src="https://user-images.githubusercontent.com/7620947/109094738-3ef35200-76f9-11eb-9270-0fd5dec553eb.png" />
</p>

Ao finalizar, o fluxo de trabalho criado anteriormente será inciado. Você pode acompanhar o processo através da aba Actions.
Se tudo ocorrer como planejado, o seu pull request indicará a falha, impedindo a integraço do código, como mostrado abaixo.

<p align="center">
    <img width="70%" src="https://user-images.githubusercontent.com/7620947/109094542-e623b980-76f8-11eb-89f9-a93c2aaf2eff.png" />
</p>
