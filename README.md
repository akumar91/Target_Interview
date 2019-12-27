Pre-requisite:

1. Install mongodb
2. Java 8
3. Intellij, eclipse or any editor of choice for java
4. Insomnia, Postman or rest client of choice

* Setup mongodb:

1. Start mongodb using the following command
brew services start mongodb

2. Create database called products
use products

3. Create collection called details
db.details.insert({"productId":13860428,"currencyCode":"USD","value":29.20})

* Setup Project in Editor

File->Open-><Project Name>

* Run the project
1. From the editor
Right click MyRetailApplication and then Run 'MyRetailApplication'
2. From command using gradle
gradle bootRun

* Try following API in rest client

Documentation is available at src/main/resources/api.yml

Sample:
(1) GET request to retrieve product details. URI structure used: /products/{id}

(2) PUT request to update the product price in data store. URI structure used: /products/{id}





