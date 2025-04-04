const express = require("express");
const Reservation = require("../models/Reservation");

const router = express.Router();

// Create a reservation
router.post("/", async (req, res) => {
  try {
    // Destructure and ensure you get all necessary fields
    const { userId, itemId, itemType, travelers, options, totalPrice, personalInfo } = req.body;

    // Create a new reservation using the correct data
    const newReservation = new Reservation({
      userId,
      itemId,
      itemType,
      travelers,
      options,
      totalPrice,
      personalInfo,
    });

    // Save the reservation to the database
    await newReservation.save();

    // Respond with the created reservation
    res.status(201).json(newReservation);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

// Get all reservations
router.get("/", async (req, res) => {
  try {
    const reservations = await Reservation.find();
    res.json(reservations);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

module.exports = router;
