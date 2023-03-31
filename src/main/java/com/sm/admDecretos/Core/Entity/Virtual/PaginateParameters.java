package com.sm.admDecretos.Core.Entity.Virtual;

public class PaginateParameters {
   private String name = "";
   private String operation = "";
   private String value = "";

   public PaginateParameters() {
       super();
   }
    public PaginateParameters(String name, String value){
       this.name = name;
       this.operation = "=";
       this.value = value;
   }
    public PaginateParameters(String name, String value, String operation){
        this.name = name;
        this.operation = operation;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
