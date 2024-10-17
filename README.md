
# TheRaven Task Submission

This repository contains my submission for the TheRaven test task.
I've diligently implemented the specified requirements and functionalities outlined in the task description.
The codebase reflects my understanding and application of best practices in software development.

Throughout this project, I aimed to craft a robust and efficient solution, ensuring scalability and adherence
to industry standards. The implemented features underscore my proficiency in leveraging technologies
to address complex challenges effectively.

I welcome feedback and suggestions for further enhancements or improvements.
Thank you for reviewing my submission.


# Initial Setup

To set up the application, you need to run the provided `InitScript.sql` on your PostgreSQL client.
This script will create the required schema and a new user that the application will connect to.

1. Log in to PostgreSQL as the root user: 
      
   ```bush
    sudo -u postgres psql

2. Run the initialization script:
    ```bush
    \i /path/to/InitScript.sql
3. The script will:
   - **Create a new schema for the application.**
   - **Set up a new user with the necessary permissions.**

Make sure the connection details in `application.properties` are updated accordingly with the new user credentials.
# API Documentation

## /api/customers (POST)
- **Description:**  Adds a new customer based on the provided data in the request body.
  - **Request body:**
    ```json
     {
        "fullName": "String (2..50 chars including whitespaces)"
        "email": "String (2..100 chars, unique, should include exactly one @)"
        "phone": "String (6..14 chars, only digits, should start from +, optional field)"
     }

  - **Responce body:**
    ```json
      {
         "id": Long
         "fullName": "String"
         "email": "String"
         "phone": "String"
      }
  - **Response**
  - **Code 201** upon successful user creation.
  - **Code 400** for incorrect or conflicting data.
  
  
    
## /api/customers (GET)

- **Description:** Returns a list of all customers in JSON format
  - **Response**
      ```json
     [
        {
          "id": Long
          "fullName": "String"
          "email": "String"
          "phone": "String"
        },
        {
          "id": Long
          "fullName": "String"
          "email": "String"
          "phone": "String"
        }
    ]

- **Code 200** upon successful retrieval.

## /api/customers/{id} (GET)
- **Description:** Returns a specific customer by ID
  - **Response body:**
    ```json
    {
      "id": Long
      "fullName": "String"
      "email": "String"
      "phone": "String"
    }

- **Response**
    - **Code 200** if the customer is found.
    - **Code 400** if the customer is not found.

## /api/customers/{id} (PUT)
- **Description:** Edits an existing customer's details.
- **Request body:**
  ```json
   {
      "id": Long
      "fullName": "String (2..50 chars including whitespaces)"
      "email": "String (2..100 chars, unique, should include exactly one @)"
      "phone": "String (6..14 chars, only digits, should start from +, optional field)"
   }

- **Responce body:**
  ```json
    {
       "id": Long
       "fullName": "String"
       "email": "String"
       "phone": "String"
    }
- **Response**
- **Code 200** if the update is successful
- **Code 400** if the customer is not found.

## /api/customers/{id} (DELETE)
- **Description:** Just mark the client as deleted and hide it to GET requests, but leave its data in the DB.
- **Response**
- **Code 204** if the deletion is successful.
- **Code 400** if the customer is not found.