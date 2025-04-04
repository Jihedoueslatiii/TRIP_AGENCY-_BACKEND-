const mongoose = require("mongoose");

const ReservationSchema = new mongoose.Schema({
  userId: { type: String, required: true }, 
  itemId: { type: String, required: true }, 
  itemType: { 
    type: String, 
    enum: ["offer", "flight", "accommodation"], 
    required: true 
  },
  travelers: { type: Number, required: true }, 
  options: { type: [String], default: [] }, 
  totalPrice: { type: Number, required: true }, 
  personalInfo: {
    name: { type: String, required: true }, 
    email: { type: String, required: true }, 
    phone: { type: String, required: true } 
  },
  paymentStatus: { 
    type: String, 
    enum: ["pending", "paid", "failed"], 
    default: "pending" 
  }, 
  createdAt: { type: Date, default: Date.now }, 
  updatedAt: { type: Date, default: Date.now }
});

// Middleware to update the 'updatedAt' field on updates
ReservationSchema.pre('save', function(next) {
  this.updatedAt = Date.now();
  next();
});

module.exports = mongoose.model("Reservation", ReservationSchema);
