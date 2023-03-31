package com.sm.admDecretos.Core.Entity.Virtual;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

public class PaginateList {
   private int page = 0;
   private int size = 10;
   private List<PaginateParameters> paginateParametersList = new ArrayList<>();
   @JsonIgnoreProperties
   private List<PaginateOrder> orderFieldsList = new ArrayList<>();

   public PaginateList() {
       super();
   }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public List<PaginateParameters> getPaginateParametersList() {
        return paginateParametersList;
    }

    public void setPaginateParametersList(List<PaginateParameters> paginateParametersList) {
        this.paginateParametersList = paginateParametersList;
    }

    public List<PaginateOrder> getOrderFieldsList() {
        return orderFieldsList;
    }

    public void setOrderFieldsList(List<PaginateOrder> orderFieldsList) {
        this.orderFieldsList = orderFieldsList;
    }

    public void addParameter(PaginateParameters paginateParameter){
        this.paginateParametersList.add(paginateParameter);
    }
    
    public PaginateParameters getParameterByName(String name){
        for(PaginateParameters p: this.paginateParametersList){
            if(p.getName().equalsIgnoreCase(name)){
                return p;
            }
        }
        return null;
    }
}
