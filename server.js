require("dotenv").config();
const express = require("express");
const mongoose = require("mongoose");
const cors = require('cors');
const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);

const app = express();
app.use(cors());
app.use(express.json()); 

app.post('/create-checkout-session', async (req, res) => {
    try {
        const session = await stripe.checkout.sessions.create({
            payment_method_types: ['card'],
            line_items: [{
                price_data: {
                    currency: 'eur',
                    product_data: {
                        name: 'Reservation',
                    },
                    unit_amount: req.body.amount,
                },
                quantity: 1,
            }],
            mode: 'payment',
            success_url: 'http://localhost:59571/success', // Replace for production
            cancel_url: 'http://localhost:59571/cancel', // Replace for production
        });

        res.json({ sessionId: session.id });
    } catch (error) {
        console.error('Error creating checkout session', error);
        res.status(500).json({ error: error.message });
    }
});

const PORT = process.env.PORT || 5000;
const MONGO_URI = process.env.MONGO_URI || "mongodb://localhost:27017/reservations";

mongoose
    .connect(MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true })
    .then(() => console.log("MongoDB connected"))
    .catch((err) => console.error("MongoDB connection error:", err));

app.get("/", (req, res) => {
    res.send("Reservation Microservice is running");
});

const reservationRoutes = require("./routes/reservationRoutes");
app.use("/api/reservations", reservationRoutes);

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});