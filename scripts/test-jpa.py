#!/usr/bin/python
import requests

#Request 1 GET
response = requests.get("http://localhost:8080/jpa-products/1")
request_1 = response.json()
print request_1

#Request 2 GET
response = requests.get("http://localhost:8080/jpa-products/1")
request_2 = response.json()
print request_2

#Request 2 Update
request_2["name"] = "New awsome TV"
response = requests.put("http://localhost:8080/jpa-products", json = request_2)
print response.json()

#Request 1 Update (Blows up on a lock exception)
request_1["name"] = "Newer awesomer flatscreen TV"
response = requests.put("http://localhost:8080/jpa-products", json = request_1)
print response.json()
