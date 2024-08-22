# Inventory API

## Add Inventory
Endpoint: POST /api/inventory/add

Request Body:
```json
{
  "name": "Product Name",
  "price": 1500000,
  "quantity": 100
}
```

Response Body (200, Success):
```json
{
  "data": {
    "id": 1,
    "name": "Product Name",
    "price": 1500000,
    "quantity": 100
  },
  "errors": null
}
```

Response Body (400, Bad Request):
```json
{
  "data": null,
  "errors": "name: must not be blank"
}
```

## Update Inventory
Endpoint: PUT /api/inventory/update/{inventoryId}

Request Body:
```json
{
  "name": "Product Name",
  "price": 1500000,
  "quantity": 100
}
```

Response Body (200, Success):
```json
{
  "data": {
    "id": 1,
    "name": "Product Name",
    "price": 1500000,
    "quantity": 100
  },
  "errors": null
}
```

Response Body (400, Bad Request):
```json
{
  "data": null,
  "errors": "error message"
}
```

Response Body (404, Not Found):
```json
{
  "data": null,
  "errors": "Inventory not found."
}
```