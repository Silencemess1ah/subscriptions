```git clone https://github.com/Silencemess1ah/subscriptions.git```

```cd subscriptions``` 

```chmod +x run.sh``` 

```chmod +x stop.sh```

Для запуска нужен Docker + Gradle

```./run.sh``` 

поднимется Docker контейнер с постгрес и запущенном на порту 8080 приложением

для остановки 

```./stop.sh```

API :

Пользователи:

POST /v1/users — создать пользователя

GET /v1/users/{id} — получить пользователя

PUT /v1/users/{id} — обновить пользователя

DELETE /v1/users/{id} — удалить пользователя

Подписки:

POST /v1/users/{id}/subscriptions — добавить подписку

GET /v1/users/{id}/subscriptions — получить все подписки пользователя

DELETE /v1/users/{id}/subscriptions/{sub_id} — удалить подписку

GET /v1/subscriptions/top — получить ТОП-3 подписок

P.S. создано на скорую руку )
