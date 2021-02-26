# Roteiro - Integração Contínua

Integração contínua e entrega contínua são práticas muito comuns em times de desenvolvivemnto que empregram metodologias ágeis. Especificamente, tais práticas buscam automatizar o ciclo de vida das aplicações, aplicando práticas de garantia que qualidade, testes e distribuição. Atualmente existem diversas ferramentas que auxiliam os desenvolvedores nesse processo, criando rotinas que são acionadas antes da integração do código a base principal e também após integração para distribuição de uma versão atualizada do software.

Atualmente, existem diversas ferramentas que auxliam os profissionais no processo de desenvolvimento. Essas ferramentas geralmente são integradas aos serviços que armazenam e versionam o código fonte, tais como GitHub, GitLab, entre outros. No caso do Github, a plataforma oferece uma ferramenta nativa chamada Github Actions, que pode ser acessada na barra superior do repositório como mostrado abaixo.

![image](https://user-images.githubusercontent.com/7620947/109080916-232f8200-76e0-11eb-8d02-9ca9f518cea2.png)

O Github Actions permite a criação de fluxos de trabalho que podem ser disparados a partir de eventos da plataforma, tais como Pull Requests, Git Push, abertura de issues, entre outras coisas. Desta forma o usuário pode definir tarefas e automatizar execução de teste unitários, linters e distribuição da aplicação após integrada.

Neste roteiro, você irá aprender a configurar um simples fluxo de integração contínua para uma aplicaçãoo desenvolvida em Java. A partir de um Pull Request ou Push, o GitHub Actions vai executar os testes unitários e compilar a nossa aplicação

![image](https://user-images.githubusercontent.com/7620947/109091107-f2a51380-76f2-11eb-9004-ecfbdc2d0e64.png)

Para isso, nós precisamos criar o arquivo `.github/workflows/actions.yaml`, ele será responsável por definir as instruções do nosso fluxo de tarefas. O conteúdo do arquivo será definido da seguinte forma:

```yaml
name: Github CI
on: [push, pull_request] # Eventos que irão iniciar a tarefa

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
        run: mvn test # Executada os testes unitários

      - name: Build
        run: mvn package -Dmaven.test.skip=true # Compila o código fonte
```

Após criar o arquivo, o GitHub Actions iniciará automaticamente o fluxo de tarefass e você pode acompanhar todo o processo através da aba Actions do seu repositório

![image](https://user-images.githubusercontent.com/7620947/109092561-8677df00-76f5-11eb-9db1-b2409505b721.png)

Para finalizar, nós iremos criar um pull request com um pequeno bug, para verificar se o nosso código será barrado durante o process. Desta forma, você deve alterar a função `soma` no arquivo [src/main/java/br/ufmg/dcc/StackTest.java](https://github.com/rodrigo-brito/roteiro-github-actions/blob/main/src/main/java/br/ufmg/dcc/StackTest.java). Você deve alterar a linha 6, alterando o cálculo para `x + y + 1`, como apresentado abaixo. Logo, os testes devem falhar após a alteração do código.

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

![image](https://user-images.githubusercontent.com/7620947/109094738-3ef35200-76f9-11eb-9270-0fd5dec553eb.png)

Ao finalizar, o fluxo de trabalho criado anteriormente será inciado. Você pode acompanhar o processo através da aba Actions.
Se tudo ocorrer como planejado, o seu pull request indicará a falha, impedindo a integraço do código, como mostrado abaixo.

![image](https://user-images.githubusercontent.com/7620947/109094542-e623b980-76f8-11eb-89f9-a93c2aaf2eff.png)
