# parkinglot

**Proposed Design:**

![parkingslotDesign drawio](https://user-images.githubusercontent.com/36749276/212159009-636d06bb-7b4b-457a-aed6-ab84d670b88c.png)

The system is divded into three components:

* AllocationService : This service is responsible for allocating a spot for car if available in following order
* - If a Small car arrives and –
i. If we have a small slot then we should allocate a Small Slot
ii. If No Small slot if free then Medium slot
iii. If No Medium is free then Large slot
iv. If no Large is free then XLarge slot
v. If no XLarge is available then don’t print the slot, print no SLOT FOUND

* DeallocationService: This service is responsible for freeing a spot which was previously allocated to car
* ParkingDataService: This service is responsible for all data interactions with db

**Coded solution has all these services as a seperated endpoints in a single service due to time constraint **

**DB Schema:**

![parkinglot](https://user-images.githubusercontent.com/36749276/212156782-69c5fc92-f053-4a2b-a358-c3ac8cec837e.png)

*Postman Collection :*

```json
{
	"info": {
		"_postman_id": "e9b54c79-a109-4e1f-bc35-cc9009590ec6",
		"name": "parkinglot",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "10737951"
	},
	"item": [
		{
			"name": "allocate",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\n   \n        \"licenseNo\" : \"531\",\n        \"size\":\"S\"\n    \n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/parking/allocate/0ad828ee-9252-11ed-a1eb-0242ac120001",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"parking",
						"allocate",
						"0ad828ee-9252-11ed-a1eb-0242ac120001"
					],
					"query": [
						{
							"key": "",
							"value": null,
							"disabled": true
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "deallocate",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\n    \n}"
				},
				"url": {
					"raw": "http://localhost:8080/parking/deallocate/0ad828ee-9252-11ed-a1eb-0242ac120001/6f52bc60-9254-11ed-a1eb-0242ac120003",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"parking",
						"deallocate",
						"0ad828ee-9252-11ed-a1eb-0242ac120001",
						"6f52bc60-9254-11ed-a1eb-0242ac120003"
					]
				}
			},
			"response": []
		}
	]
}
```
