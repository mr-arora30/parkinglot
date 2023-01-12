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
