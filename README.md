# Getting Started

This is an example SpringBoot project which demonstrates the [IETF JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902#section-4.1) standard as
further discussed and evaluated in this article.

## How to Run
 * Open the project in IntelliJ Idea or Eclipse.
 * Run the class `SbHttpPatchDemoApplication.java` file in `io.github.isuru89.sbpatch` package.
 * Server should be running in 8080 port.

## Example Curls

 * Creating a user with a single email
 ```shell
curl --location --request POST 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userName": "jondoe",
    "firstName": "Jon",
    "lastName": "Doe",
    "primaryEmail": "jondoe@gmail.com",
    "secondaryEmails": [
        {
            "email": "email1@gmail.com"
        }
    ]
}'
```

 * Reading back the created user. (Replace the `id` with the `id` in returned response of the created user)
```shell
curl --location --request GET 'http://localhost:8080/users/{id}'
```

 * Patch user's firstName (Replace the `id` with your created user id in the url)
```shell
curl --location --request PATCH 'http://localhost:8080/users/{id}' \
--header 'Content-Type: application/json' \
--data-raw '[
    { "op": "replace", "path": "/firstName", "value": "Jon Updated" }
]'
```

  * Add a new email address to the end of email list and make it primary
```shell
curl --location --request PATCH 'http://localhost:8080/users/{id}' \
--header 'Content-Type: application/json' \
--data-raw '[
    { "op": "add", "path": "/secondaryEmails/-", "value": { "email": "email2@yahoo.com" }},
    { "op": "add", "path": "/primaryEmail", "value": "email2@yahoo.com" }
]'
```

 * Remove the first email address in the list
```shell
curl --location --request PATCH 'http://localhost:8080/users/{id}' \
--header 'Content-Type: application/json' \
--data-raw '[
    { "op": "remove", "path": "/secondaryEmails/0" }
]'
```

Try changing patch body according to the standard and see how it works.

Enjoy!