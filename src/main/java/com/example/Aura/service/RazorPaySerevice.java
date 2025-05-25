package com.example.Aura.service;

import com.example.Aura.io.RazorpayOrderResponse;
import com.razorpay.RazorpayException;

public interface RazorPaySerevice {


   RazorpayOrderResponse creatOrder(Double amount , String currency) throws RazorpayException;



}
