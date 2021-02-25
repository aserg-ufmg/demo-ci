# Roteiro - Integraço Contínua

Integração contínua e entrega contínua são práticas muito comuns em times de desenvolvivemnto que empregram metodologias ágeis. Especificamente, tais práticas buscam automatizar o ciclo de vida das aplicações, aplicando práticas de garantia que qualidade, testes e distribuição. Atualmente existem diversas ferramentas que auxiliam os desenvolvedores nesse processo, criando rotinas que são acionadas antes da integração do código a base principal e também após integração para distribuição de uma versão atualizada do software. 

Atualmente, existem diversas ferramentas que auxliam os profissionais no processo de desenvolvimento. Essas ferramentas geralmente são integradas aos serviços que armazenam e versionam o código fonte, tais como GitHub, GitLab, entre outros. No caso do Github, a plataforma oferece uma ferramenta nativa chamada Github Actions, que pode ser acessada na barra superior do repositório como mostrado abaixo.

![image](https://user-images.githubusercontent.com/7620947/109080916-232f8200-76e0-11eb-8d02-9ca9f518cea2.png)

O Github Actions permite a criação de fluxos de trabalho que podem ser disparados a partir de eventos da plataforma, tais como Pull Requests, Git Push, abertura de issues, entre outras coisas. Desta forma o usuário pode definir tarefas e automatizar execução de teste unitários, linters e distribuição da aplicação após integrada.

Neste roteiro, você irá aprender a configurar um simples fluxo de integração contínua para uma aplicaçãoo desenvolvida em Java.


