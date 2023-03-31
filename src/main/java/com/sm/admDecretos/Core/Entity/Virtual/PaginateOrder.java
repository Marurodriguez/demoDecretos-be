package com.sm.admDecretos.Core.Entity.Virtual;

public class PaginateOrder {
   private String fieldName = "";
   private String orderType = ""; //asc or desc

    public PaginateOrder() {
        super();
    }
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
