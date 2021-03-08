##Kotlin Task - order-service

###  Run Application:

```shell
gradlew clean build
gradlew bootRun
```
### Post orders

```shell
    curl -X  POST http://localhost:8080/orderFruits --data "{\"fruits\":\"Apple,Orange\"}" -H "Content-Type: application/json"
```