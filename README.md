# shop-cli

A tiny shop cli written in Java to allow distribution associates to pick product. 

# Table of Contents 
1. [Dependency Prerequisites](#dependency-prerequisites)
2. [Running the CLI](#running-the-cli)
3. [Supported Flows](#supported-flows)
4. [Error Flows](#error-flows)
5. [Further Thoughts](#further-thoughts)

## Dependency Prerequisites 

- Docker v19
- Java 8

### Backend Server Built and Running 

#### Build the Backend Server
```
docker build -t <REPOSITORY> ./backend/Dockerfile
```
(REPOSITORY can be whatever name you want)

#### Find Available Builds
```
docker image list
```

When you execute this command you should see something like:
![docker image list command](./images/docker_image_list.png)

#### Run the Backend Server
```
docker run -dp 8080:8080 <REPOSITORY>
```

#### Find the Running Container 
```
docker container list
```

When you execute this command, you should see something like: 
![docker container list command](./images/docker_container_list.png)

#### Killing the Running Container
```
docker container kill <CONTAINER ID>
```

## Running The CLI

#### Run the (latest) jar 
```
java -jar app/shop-cli.jar
```

#### Build it (if it's not already built) and you made changes
```
./gradlew clean build
```

#### Run new Build with Java
```
java -jar build/libs/shop-cli.jar
```

## Supported Flows

#### Login
![login page](images/app/happy-path/login.png)

#### Order Inquiry
![order inquiry screen](images/app/happy-path/order_inquiry.png)

#### Order Product Picking 
![product page screen](images/app/happy-path/product_page.png)

#### Summary Screen 
![summary screen](images/app/happy-path/summary_screen.png)

## Error Flows

#### Bad Login
![unsuccessful login page](images/app/error-screens/bad_login.png)

#### Order Inquiry Unsuccessful
![order inquiry failed lookup](images/app/error-screens/order_not_found.png)

#### Order Product - Over Picking 
![product page over picking](images/app/error-screens/overpicked_quantity.png)

#### Order Product - Under Picking 
![product page under picking](images/app/error-screens/negative_quantity_picked.png)

#### Order Product - Invalid Command 
![product page inavlid command](images/app/error-screens/invalid_command.png)

## Further Thoughts

- Testing the Console is very strange.
- At a second glance, I don't like this recursive design. It's complicated to test.
- I didn't keep SR (single responsibility) in mind when designing.
- I should set up an abstract class structure for my screens, so I can maintain the same "Command:" functionality and standardize and displays.
- The screens should be decoupled and the flow not as nested.
  - the flow should have been:
    - auth <- login (this part was done)
    - auth -> inquiry (this part was done)
    - order <- inquiry
    - order -> page view
    - picked order <- page view

