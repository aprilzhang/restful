# Introduction

The Account application was developed to provide simple internal account transfer function. 

# Running The Application

To test the example application run the following commands.

* To setup the h2 database run.

        java -jar target/account-demo-1.0-SNAPSHOT.jar db migrate example.yml

* To run the server run.

        java -jar target/account-demo-1.0-SNAPSHOT.jar server example.yml

* To see a list of accounts

	http://localhost:8080/accounts

* To see an individual account

	http://localhost:8080/accounts/{accountId}

* To post a new transfer.

	curl -H "Content-Type: application/json" -X POST -d '{"fromAccountId":{accountId},"toAccountId":{accountId},"amount":{amount}' http://localhost:8080/accounts/transfer
