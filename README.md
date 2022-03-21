# garage
Garage parking system

This project is garage parking system.

# For vehicle entry

  There is 3 type Car Type Enum(CAR, JEEP, TRUCK)
  # METHOD : Post
  # url : http://localhost:8080/api/garage
  # body : JSON
    Request:
    [
      {
        "carType": "JEEP",
        "color": "BLUE",
        "plateNumber": "35-AS-6283"
      },
      {
        "carType": "TRUCK",
        "color": "RED",
        "plateNumber": "35-XXX-6283"
      }
    ]
     Response:
      35-AS-6283 BLUE parking.
      35-XXX-6283 RED parking.



# For garage status
  # METHOD : Get
  # url : http://localhost:8080/api/garage
  # Response:
  {
    "35-AS-6283 BLUE": [
        "0",
        "1"
    ],
    "35-XXX-6283 RED": [
        "3",
        "4",
        "5",
        "6"
    ]
  }
  
  

# For leave car
  You can delete many car same time
  To take out the car you should use your ticket.
  
  # METHOD : Delete
  # url :  http://localhost:8080/api/garage
  # Request:
  [
    "35-AS-6283 BLUE"
  ]
  Response:
  35-AS-6283 leaving.
  
  
İnclude unit-tests.
