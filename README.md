**Travix - Problem to be solved**

You can test the application from cmd (given mvn is visible on path) after steping into project folder run:
  mvn spring-boot:run

Run unit test:
  mvn test

Application context path:
 /busyFlights

Rest API path:
  /api/v1

Flight search API path:
  /flights/search

Sample request that is exptected to return a result of 4 flights:
http://localhost:8080/busyFlights/api/v1/flights/search?origin=LHR&destination=AMS&departureDate=2017-02-01&returnDate=2017-02-10&numberOfPassengers=1

* I have provided a fake API for crazyAir and toughJet, both url are defined in src/main/resources/application.yml
If you need to replace the fake APIs with real ones, then replace the defined values within application.yml and set fakeApi to false, ex;

supplier:
  endpoint:
    search:
      fakeApi: false
      crazyAir: http://crazyAir/api/search
      toughJet: http://toughJet/api/search

Browse API documentation on:
  http://localhost:8080/busyFlights/swagger-ui.html#

Sample from application health services:
  http://localhost:8080/busyFlights/health

* Further additions would be:
- Adding custom error messages and codes for our API.
- Externalize application configuration in order to be able to change it without the need to unwrap the jar.


**Background:**

BusyFlights is a flights search solution which aggregates flight results initially from 2 different suppliers (CrazyAir and ToughJet). A future iteration (not part of the test) may add more suppliers.

**Recommended duration of test:**

Allocate no more than 90 minutes to complete this exercise. An incomplete solution is acceptable but documenting any remaining tasks and next steps is expected. 

**What is required:**

Use this GitHub repository as a base to implement the Busy Flights service that should produce an aggregated result from both CrazyAir and ToughJet.
The result should be a JSON response which contains a list of flights ordered by fare which has the following attributes:

**Busy Flights API**

**Request**

| Name | Description |
| ------ | ------ |
| origin | 3 letter IATA code(eg. LHR, AMS) |
| destination | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_LOCAL_DATE format |
| returnDate | ISO_LOCAL_DATE format |
| numberOfPassengers | Maximum 4 passengers |

**Response**

| Name | Description |
| ------ | ------ |
| airline | Name of Airline |
| supplier | Eg: CrazyAir or ToughJet |
| fare | Total price rounded to 2 decimals |
| departureAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| destinationAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_DATE_TIME format |
| arrivalDate | ISO_DATE_TIME format |

The service should connect to the both the suppliers using HTTP.

**CrazyAir API**

**Request**

| Name | Description |
| ------ | ------ |
| origin | 3 letter IATA code(eg. LHR, AMS) |
| destination | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_LOCAL_DATE format |
| returnDate | ISO_LOCAL_DATE format |
| passengerCount | Number of passengers |

**Response**


| Name | Description |
| ------ | ------ |
| airline | Name of the airline |
| price | Total price |
| cabinclass | E for Economy and B for Business |
| departureAirportCode | Eg: LHR |
| destinationAirportCode | Eg: LHR |
| departureDate | ISO_LOCAL_DATE_TIME format |
| arrivalDate | ISO_LOCAL_DATE_TIME format |

**ToughJet API**

**Request**

| Name | Description |
| ------ | ------ |
| from | 3 letter IATA code(eg. LHR, AMS) |
| to | 3 letter IATA code(eg. LHR, AMS) |
| outboundDate |ISO_LOCAL_DATE format |
| inboundDate | ISO_LOCAL_DATE format |
| numberOfAdults | Number of passengers |

**Response**

| Name | Description |
| ------ | ------ |
| carrier | Name of the Airline |
| basePrice | Price without tax(doesn't include discount) |
| tax | Tax which needs to be charged along with the price |
| discount | Discount which needs to be applied on the price(in percentage) |
| departureAirportName | 3 letter IATA code(eg. LHR, AMS) |
| arrivalAirportName | 3 letter IATA code(eg. LHR, AMS) |
| outboundDateTime | ISO_INSTANT format |
| inboundDateTime | ISO_INSTANT format |

**What you need to provide:**

- A solution that meets the above requirements.
- The implementation should be made as close to 'production ready' as possible within the time constraints.

It is fine to change any of the supplied application code, if you choose to do so please add comments to indicate what has changed and why.

**Note**

Please clone this project then create your own repository from it. Do not fork/branch this project when creating your solution as it will be visible to other applicants.
