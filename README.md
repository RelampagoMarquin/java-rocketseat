# java-rocketseat
Repositorio criado durante o curso gratuito da rocketseat, no qual foi criado uma simples api de todolist

# Executando
via cli sem build
```bash
nvm spring-boot:run # necessario ter o maven instaldo
```
Com build
```bash
mvn clean install # vai fazer o build da aplicação
java -jar /target/todolist-1.0.0.jar #executa o build do projeto
```

# rotas disponiveis
url padrão:
online: https://todolist-n8bq.onrender.com
caso esteja usando localmente: http:localhost:8080

usuario:
    POST /user/

Tasks:
    POST /tasks/
    GET /tasks/
    PUT /tasks/
