#!/usr/bin/python
import requests

#Request A GET
response = requests.get("http://localhost:8080/jdbc-products/1")
request_1 = response.json()
print "User A:", request_1

#Request B GET
response = requests.get("http://localhost:8080/jdbc-products/1")
request_2 = response.json()
print "User B:", request_2

#Request B Update
request_2["name"] = "New awsome TV"
response = requests.put("http://localhost:8080/jdbc-products", json = request_2)
print "User B:", response.json()

#Request A Update (Blows up on a lock exception)
request_1["name"] = "Newer awesomer flatscreen TV"
response = requests.put("http://localhost:8080/jdbc-products", json = request_1)
print "User A:", response.json()
