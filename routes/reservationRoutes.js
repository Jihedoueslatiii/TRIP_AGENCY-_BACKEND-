const express = require("express");
const axios = require("axios");
const Reservation = require("../models/Reservation");
const router = express.Router();

// Base URL for the OffreVoyage microservice
const OFFRE_SERVICE_BASE_URL = process.env.OFFRE_SERVICE_BASE_URL || "http://localhost:8093/offresvoyage";

router.post("/", async (req, res) => {
  try {
    const offerResponse = await axios.get(`${OFFRE_SERVICE_BASE_URL}/${req.body.idOffreVoyage}`);
    const offerDetails = offerResponse.data;

    if (!offerDetails || !offerDetails.prix) {
      return res.status(400).json({ error: "Offer details are missing or invalid" });
    }

    const prixTotal = offerDetails.prix * req.body.travelers;

    const reservation = new Reservation({
      userId: req.body.userId,
      itemId: req.body.idOffreVoyage,      // ✅ match schema
      itemType: "offre",
      travelers: req.body.travelers,
      options: req.body.options,
      totalPrice: prixTotal,               // ✅ match schema
      personalInfo: req.body.personalInfo,
      paymentStatus: req.body.paymentStatus || "pending"
    });

    await reservation.save();
    res.status(201).json(reservation);

  } catch (error) {
    console.error('Error creating reservation:', error);
    res.status(400).json({ error: error.message });
  }
});



// Get all reservations (You might want to rename this route to something like /all-reservations if needed)
router.get("/", async (req, res) => {
  try {
    const reservations = await Reservation.find();
    res.status(200).json(reservations); // Return all reservations
  } catch (error) {
    res.status(500).json({ error: error.message }); // Handle server errors
  }
});

// Get specific offer details from the OffreVoyage microservice
router.get("/offre/:offreId", async (req, res) => {
  try {
    const { offreId } = req.params;

    // Fetch offer details by ID from the OffreVoyage microservice
    const response = await axios.get(`${OFFRE_SERVICE_BASE_URL}/${offreId}`);

    // Check if the offer details exist
    if (!response.data) {
      return res.status(400).json({ error: "Offer not found" });
    }

    // Return the fetched offer details
    res.status(200).json(response.data);
  } catch (error) {
    console.error("Error fetching offer details:", error.message);
    res.status(500).json({ error: "Failed to fetch offer details" });
  }
});

// Update a reservation by ID
router.put("/:id", async (req, res) => {
  try {
    const reservation = await Reservation.findByIdAndUpdate(
      req.params.id,
      req.body,
      { new: true, runValidators: true }
    );
    if (!reservation) {
      return res.status(404).json({ error: "Reservation not found" });
    }
    res.status(200).json(reservation); // Return the updated reservation
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
});

// Delete a reservation by ID
router.delete("/:id", async (req, res) => {
  try {
    const reservation = await Reservation.findByIdAndDelete(req.params.id);
    if (!reservation) {
      return res.status(404).json({ error: "Reservation not found" });
    }
    res.status(200).json({ message: "Reservation deleted successfully" }); // Successful deletion message
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Get a single reservation by ID
router.get("/:id", async (req, res) => {
  try {
    const reservation = await Reservation.findById(req.params.id);
    if (!reservation) {
      return res.status(404).json({ error: "Reservation not found" });
    }
    res.status(200).json(reservation); // Return the reservation
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;

