# Advait_assignment

Supported version :: Java 17, Spring boot 3.2.2, Eclipse
There are 3 main package: Controller, Service and repository
Endpoint present in : UserController class
Business logic present in UserService class.

Ignore test package

# Token
Mutiple ways to validating token,Here created TokenUtil class, I have not created the security adapter in order to validate, Directlt getting from header. All the API are in working state 

# Database
Database connection detail present in application.properties

# Below are the request and response in order to validate it.


# API 1:
URL POST:: localhost:8080/api/user/sign-up

Request: {
    "email": "abc@gmail.com",
    "password": "abc1123",
    "userName": "John"
}

Reponse:: 
User created successgully

# API2 POST:
URL:: localhost:8080/api/user/sign-in

{"email":"abc@gmail.com","password":"abc1123","userName":"John"}

Reponse:: 
{
    "accessToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huIiwiaWF0IjoxNzM0NjEzMzI2LCJleHAiOjE3MzQ2MTQyMjZ9.NgtpZAnP5CwaCzt4YJaDwk8ranBZ3--Dkc7vOiLUHx8",
    "refreshToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huIiwiaWF0IjoxNzM0NjEzMzI2LCJleHAiOjE3MzQ2MTY5MjZ9.p4xGSsh8-eSbTBgABnf6tU1eFlmjvxMWn8vuAa31dlo"
}

# API3 POST: localhost:8080/api/user/authorize-token

Request:: {"email":"abc@gmail.com","password":"abc1123","userName":"John"}
In Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huIiwiaWF0IjoxNzM0NjEyNjEwLCJleHAiOjE3MzQ2MTM1MTB9.c2VpxoB0hCxs7kAgjiD3R_IXdtzgMQpOKdCO7iZXX8A

Response
Token is valid


# API4 POST:: localhost:8080/api/user/refresh-token

Request: 
{
    "refreshToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huIiwiaWF0IjoxNzM0NjEyNjEwLCJleHAiOjE3MzQ2MTYyMTB9.VzdsMbrVaeY_vtzWYrRjIK2iMI-PwedTH4r_deoyj7s"
}

Reponse::

{
    "accessToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huNSIsImlhdCI6MTczNDYxMzc1OSwiZXhwIjoxNzM0NjE0NjU5fQ.zmoikaQrE1n9kxuPFng1Py8ZJKp2oGYd0y-WxG9eiuk",
    "refreshToken": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huNSIsImlhdCI6MTczNDYxMzc1OSwiZXhwIjoxNzM0NjE3MzU5fQ._F15w9sVo16XPFclpkNZPfxK7YYABjXxwkkF0GNwPcY"
}


# API5: Get: localhost:8080/api/user/refresh-token

Response: Token revoked
