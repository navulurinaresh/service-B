# Employee Update API Documentation

## Overview
This document provides information about the Employee Update API endpoint, including sample request and response formats.

---

## Update Employee

### Endpoint
```
PUT /employee/update/{id}
```

### Description
Updates an existing employee's information by their ID.

### Path Parameters
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id | integer | Yes | The unique identifier of the employee to update |

### Request Headers
```
Content-Type: application/json
```

### Request Body
```json
{
  "name": "string",
  "age": integer,
  "salary": integer,
  "gender": "string"
}
```

### Sample Request

**URL:** `PUT http://localhost:8080/employee/update/1`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "name": "John Doe Updated",
  "age": 31,
  "salary": 55000,
  "gender": "Male"
}
```

### Sample Response

**Status Code:** `200 OK`

**Body:**
```json
{
  "id": 1,
  "name": "John Doe Updated",
  "age": 31,
  "salary": 55000,
  "gender": "Male"
}
```

---

## Error Responses

### Employee Not Found

**Status Code:** `404 Not Found`

**Sample Request:**
```
PUT http://localhost:8080/employee/update/999
```

**Body:**
```json
{
  "name": "Non Existent",
  "age": 25,
  "salary": 40000,
  "gender": "Male"
}
```

**Response:**
```json
{
  "timestamp": "2025-10-15T10:30:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Employee not found with id: 999",
  "path": "/employee/update/999"
}
```

---

## Additional Examples

### Example 1: Update Employee Name Only
**Request:**
```json
{
  "name": "Jane Smith",
  "age": 30,
  "salary": 50000,
  "gender": "Male"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Jane Smith",
  "age": 30,
  "salary": 50000,
  "gender": "Male"
}
```

### Example 2: Update Employee Salary
**Request:**
```json
{
  "name": "John Doe",
  "age": 30,
  "salary": 75000,
  "gender": "Male"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "age": 30,
  "salary": 75000,
  "gender": "Male"
}
```

### Example 3: Update All Employee Information
**Request:**
```json
{
  "name": "Alice Johnson",
  "age": 35,
  "salary": 80000,
  "gender": "Female"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Alice Johnson",
  "age": 35,
  "salary": 80000,
  "gender": "Female"
}
```

---

## Field Descriptions

| Field | Type | Required | Description | Example |
|-------|------|----------|-------------|---------|
| id | integer | No (auto-generated) | Unique identifier for the employee | 1 |
| name | string | Yes | Full name of the employee | "John Doe" |
| age | integer | Yes | Age of the employee in years | 30 |
| salary | integer | Yes | Annual salary in currency units | 50000 |
| gender | string | Yes | Gender of the employee | "Male" or "Female" |

---

## cURL Examples

### Update an Employee
```bash
curl -X PUT http://localhost:8080/employee/update/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe Updated",
    "age": 31,
    "salary": 55000,
    "gender": "Male"
  }'
```

### Update Employee with Different ID
```bash
curl -X PUT http://localhost:8080/employee/update/5 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sarah Connor",
    "age": 28,
    "salary": 65000,
    "gender": "Female"
  }'
```

---

## Notes

1. All fields in the request body are required even if you only want to update one field
2. The `id` field in the request body is ignored; the ID from the URL path is used
3. If the employee with the specified ID doesn't exist, a 404 Not Found error is returned
4. All numeric fields (age, salary) must be valid integers
5. The response includes all employee fields including the ID

---

## Testing

To run the test cases for the update employee service:

```bash
cd service-B
./gradlew test --tests EmployeeServiceTest
./gradlew test --tests EmployeeControllerTest
```

Or run all tests:
```bash
./gradlew test
```
