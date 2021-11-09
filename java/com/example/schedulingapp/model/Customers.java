package com.example.schedulingapp.model;

public class Customers {

    int customerId;
    String customerName;
    String customerPostalCode;
    String customerAddress;
    String customerPhone;
    int customerDivisonId;
    
    public Customers(int customerId, String customerName, String customerPostalCode, String customerAddress,
                     String customerPhone, int customerDivisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPostalCode = customerPostalCode;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerDivisonId = customerDivisionId;

    }
    public int getCustomerId(){
        return customerId;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getCustomerPostalCode(){
        return customerPostalCode;
    }

    public String getCustomerAddress(){
        return customerAddress;
    }

    public String getCustomerPhone(){
        return customerPhone;
    }

    public int getCustomerDivisonId(){
        return customerDivisonId;
    }
}

