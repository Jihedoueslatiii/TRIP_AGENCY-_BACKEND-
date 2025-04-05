# ✈️ 1. GET All Flights
# Description: Retrieve all flights from the system.
# URL: http://localhost:8093/api/flights
# Method: GET
# JSON: No body required.

# 🛫 2. GET Flight by ID
# Description: Retrieve a flight by its ID.
# URL: http://localhost:8093/api/flights/{id}
# Method: GET
# JSON: No body required.

# 🆕 3. CREATE a New Flight
# Description: Create a new flight with all required details.
# URL: http://localhost:8093/api/flights
# Method: POST
# JSON:
{
    "numVol": "LX789",
    "compagnie": "Swiss Air",
    "aeroportDepart": "ZRH",
    "aeroportArrivee": "LHR",
    "dateVol": "2025-04-07",
    "heureDepart": "09:00:00",
    "heureArrivee": "10:30:00",
    "etatVol": "PROGRAMMED"
}

# 🔄 4. UPDATE a Flight
# Description: Update an existing flight's details.
# URL: http://localhost:8093/api/flights/update
# Method: PUT
# JSON:
{
    "idVol": 1,
    "numVol": "LX789",
    "compagnie": "Swiss International Air Lines",
    "aeroportDepart": "ZRH",
    "aeroportArrivee": "LHR",
    "dateVol": "2025-04-07",
    "heureDepart": "09:00:00",
    "heureArrivee": "10:45:00",
    "etatVol": "DELAYED"
}

# ❌ 5. DELETE a Flight
# Description: Delete a flight by its ID.
# URL: http://localhost:8093/api/flights/{id}
# Method: DELETE
# JSON: No body required.

# 📅 6. GET Flights by Date
# Description: Retrieve flights on a specific date.
# URL: http://localhost:8093/api/flights/by-date?date=2025-04-07
# Method: GET
# JSON: No body required.

# ⏳ 7. GET Flight Duration
# Description: Get the flight duration for a specific flight by its ID.
# URL: http://localhost:8093/api/flights/{id}/duration
# Method: GET
# JSON: No body required.

# 🔍 8. SEARCH Flights by Departure and Arrival
# Description: Search for flights based on departure and arrival locations.
# URL: http://localhost:8093/api/flights/search?depart=ZRH&arrivee=LHR
# Method: GET
# JSON: No body required.

# 🧐 9. ADVANCED SEARCH
# Description: Advanced search for flights with multiple filtering options.
# URL: http://localhost:8093/api/flights/advanced-search?departureAirport=ZRH&arrivalAirport=LHR&startDate=2025-04-07&endDate=2025-04-07&airline=Swiss Air&status=PROGRAMMED
# Method: GET
# JSON: No body required.

