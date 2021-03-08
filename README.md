##Kotlin Task - order-service

###  Run Application:

```shell
gradlew clean build
gradlew bootRun
```
### Post orders

```shell
    curl -X  POST http://localhost:8080/orderFruits?discount=true --data "{\"fruits\":\"Apple,Apple,Apple,Orange\"}" -H "Content-Type: application/json"
```

###Notification

- Success: Order successful; total amount: <TOTAL>$ : for : <LIST> : Estimated Delivery: <DATE TIME>
