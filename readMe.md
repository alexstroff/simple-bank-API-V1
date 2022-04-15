### curl samples (application deployed in application context `Simple Bank API`).
> For windows use `Git Bash`
>
>######## ======= Clients ======= ########
>
>#### get Client with ID
>`curl -s http://localhost:8080/client/100000`
>
>#### get All Clients
>`curl -s http://localhost:8080/client/all`
>
>#### add new Client
>`curl -s -i -X POST -d '{"name":"newName","email":"newemal@yandex.ru"}' -H 'Content-Type:application/json' http://localhost:8080/client/add`
>
>>#### update Client
>`curl -X PUT -d '{"id":"100000", "name":"superNewName","email":"newemal@yandex.ru"}' -H 'Content-Type:application/json' http://localhost:8080/client/`
>
>#### delete Client
>`curl -X DELETE -H 'Content-Type:application/json' http://localhost:8080/client/100001`


>######## ======= Accounts ======= ######## 
>
>#### get Account ID for Client ID
>`curl -s http://localhost:8080/client/100000/account/100002`
>
>#### get All Accounts for Client ID
>`curl -s http://localhost:8080/client/100000/account/all`
>
>#### add new Account for Client ID 
>`curl -s -i -X POST -d '{"number":"00000000","amount":"9999","currency":"RUB"}' -H 'Content-Type:application/json' http://localhost:8080/client/100000/account`
>
>#### update new Account for Client ID
>`curl -X PUT -d '{"id":"100002","number":"00000000","amount":"9999","currency":"RUB"}' -H 'Content-Type:application/json' http://localhost:8080/client/100000/account`
>
>#### delete Account
>`curl -X DELETE -H 'Content-Type:application/json' http://localhost:8080/client/100000/account/100002`
>
>######## ======= Cards ======= ########
>
>#### get Card by Account ID and Client ID
>`curl -s http://localhost:8080/client/100000/account/100002/card/100004`
>
>#### get All Cards by Account ID and Client ID
>`curl -s http://localhost:8080/client/100000/account/100002/card/all`
>
>#### add new Card 
>`curl -s -i -X POST -d '{"account":"100002","number":"9999"}' -H 'Content-Type:application/json' http://localhost:8080/client/100000/account/100002/card`
>
>#### update new Card by Account ID and Client ID
>`curl -X PUT -d '{"id":"100002","number":"00000000","amount":"9999","currency":"RUB"}' -H 'Content-Type:application/json' http://localhost:8080/client/100000/account/100002/card`
>
 