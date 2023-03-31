package com.sm.admDecretos.Core.Repository;


import com.sm.admDecretos.Core.Entity.Db.AbstractEntity;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl<T extends AbstractEntity> {

    protected EntityManager entityManager;

    private String querySelectString = " SELECT t.* ";
    private String queryFromString = " FROM %tableName% t ";
    private String queryJString = "";
    private String queryWhString = " WHERE %whereConditions% ";
    private String queryGrupByString = "";
    private String queryHavingString = "";
    private String queryOrderString = " ORDER BY %orderByFields%";
    private String queryLimitString = " LIMIT :pos,:pageSize ";
    private String queryString = "";
    private String queryCountString = "";
    private String db = "mysql";

    public void setDb(String db) {
        this.db = db;
    }

    private List<KeyValue> keyValues = new ArrayList<>();
    private String tableName = "";
    private String whereConditions = "";
    private String orderByFields = "";
    private Long resultCount = 0L;

    private Query queryCount;
    private Query query;

    public RepositoryImpl() {
        this.init();
    }

    public void init() {
        this.querySelectString = " SELECT t.* ";
        this.queryFromString = " FROM %tableName% t ";
        this.queryJString = "";
        this.queryWhString = " WHERE %whereConditions% ";
        this.queryGrupByString = "";
        this.queryHavingString = "";
        this.queryOrderString = " ORDER BY %orderByFields%";
        this.queryLimitString = " LIMIT :pos,:pageSize ";
        this.queryString = "";
        this.queryCountString = "";

        this.keyValues.clear();
        this.tableName = "";
        this.whereConditions = "";
        this.orderByFields = "";
        this.resultCount = 0L;
    }

    public void addParameter(String key, Object value) {
        KeyValue keyValue = new KeyValue(key, value);
        this.keyValues.add(keyValue);
    }

    private void getSql() {
        this.queryFromString = this.queryFromString.replace("%tableName%", this.tableName);

        //No hay condición de where y no es un queryWhString custom
        if (this.whereConditions.isEmpty() && this.queryWhString.contains("%whereConditions%")) {
            this.queryWhString = "";
        } else {
            this.queryWhString = this.queryWhString.replace("%whereConditions%", this.whereConditions);
        }

        if (this.orderByFields.isEmpty() && this.queryOrderString.contains("%orderByFields%")) {
            this.queryOrderString = "";
        } else {
            this.queryOrderString = this.queryOrderString.replace("%orderByFields%", this.orderByFields);
        }

        switch (this.db) {
            case "mysql":
                this.queryString = this.querySelectString + this.queryFromString + this.queryJString + this.queryWhString + this.queryGrupByString + this.queryHavingString + this.queryOrderString + this.queryLimitString;
                break;
            case "oracle":
                this.queryString = this.querySelectString + this.queryFromString + this.queryJString + this.queryWhString + this.queryGrupByString + this.queryHavingString + this.queryOrderString;
                this.queryString = "SELECT * FROM ( SELECT a.*, ROWNUM AS num FROM ( " + this.queryString + ") a WHERE ROWNUM <= :pageSizeMasPos ) WHERE NUM >= :pos ";
                break;
        }

        System.out.println("Print SQL query: ");
        System.out.println(this.queryString);

        //this.queryString = this.querySelectString  + this.queryFromString  + this.queryJString  + this.queryWhString  + this.queryGrupByString  + this.queryHavingString  + this.queryOrderString  + this.queryLimitString;
        this.queryCountString = " SELECT count(*) " + this.queryFromString + this.queryJString + this.queryWhString + this.queryGrupByString + this.queryHavingString;


    }

    private void setParametersToQuery(Query queryDest) {
        for (KeyValue keyValue : this.keyValues) {
            queryDest.setParameter(keyValue.getKey(), keyValue.getValue());
        }
    }

    public Double executeScalarDouble(String nativeQuery, Double defaultValue) {
        try {
            Query queryScalar = entityManager.createNativeQuery(nativeQuery);
            Double value = Double.parseDouble(queryScalar.getSingleResult().toString());
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }


    public Page<T> createSql(Class clase, int page, int size) {
        this.getSql();

        // QueryCount
        this.queryCount = entityManager.createNativeQuery(queryCountString);
        setParametersToQuery(this.queryCount);
        this.resultCount = Long.parseLong(queryCount.getSingleResult().toString());
 
        this.query = entityManager.createNativeQuery(queryString, clase);

        Pageable pageable = PageRequest.of(page, size);
        int pos = (pageable.getPageNumber()) * pageable.getPageSize();
        if (pos < 0) {
            pos = 0;
        }
        if (this.db == "mysql") {
            this.addParameter("pos", pos);
            this.addParameter("pageSize", pageable.getPageSize());
        } else {
            //oracle paginación: http://gregorgonzalez.com.ve/blog/limit-en-oracle/
            if (pos == 0) {
                pos = 1;
            }
            this.addParameter("pos", pos);
            this.addParameter("pageSizeMasPos", pos + pageable.getPageSize() - 1);
        }
        setParametersToQuery(this.query);

        List<T> paginateList = query.getResultList();
        if (resultCount < pageable.getPageSize()) {
            pageable = PageRequest.of(0, pageable.getPageSize());
        }

        Page<T> pageResult =
                new PageImpl<>(paginateList, pageable, resultCount);

        return pageResult;
    }


//    PROPERTY


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String getQuerySelectString() {
        return querySelectString;
    }

    public void setQuerySelectString(String querySelectString) {
        this.querySelectString = querySelectString;
    }

    public String getQueryFromString() {
        return queryFromString;
    }

    public void setQueryFromString(String queryFromString) {
        this.queryFromString = queryFromString;
    }

    public String getQueryJString() {
        return queryJString;
    }

    public void setQueryJString(String queryJString) {
        this.queryJString = queryJString;
    }

    public String getQueryWhString() {
        return queryWhString;
    }

    public void setQueryWhString(String queryWhString) {
        this.queryWhString = queryWhString;
    }

    public String getQueryGrupByString() {
        return queryGrupByString;
    }

    public void setQueryGrupByString(String queryGrupByString) {
        this.queryGrupByString = queryGrupByString;
    }

    public String getQueryHavingString() {
        return queryHavingString;
    }

    public void setQueryHavingString(String queryHavingString) {
        this.queryHavingString = queryHavingString;
    }

    public String getQueryOrderString() {
        return queryOrderString;
    }

    public void setQueryOrderString(String queryOrderString) {
        this.queryOrderString = queryOrderString;
    }

    public String getQueryLimitString() {
        return queryLimitString;
    }

    public void setQueryLimitString(String queryLimitString) {
        this.queryLimitString = queryLimitString;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getWhereConditions() {
        return whereConditions;
    }

    public void setWhereConditions(String whereConditions) {
        this.whereConditions = whereConditions;
    }

    public String getOrderByFields() {
        return orderByFields;
    }

    public void setOrderByFields(String orderByFields) {
        if (orderByFields.contains(".") == false) {
            orderByFields = "t." + orderByFields;
        }
        this.orderByFields = orderByFields;
    }

    public void setOrderByFields(String orderByFields, String orderType) {
        if (orderByFields.contains(".") == false) {
            orderByFields = "t." + orderByFields;
        }
        orderByFields += " " + orderType;
        this.setOrderByFields(orderByFields);
    }

    public void setOrderByFieldsParameter(PaginateOrder parameter) {
        this.setOrderByFields(parameter.getFieldName(), parameter.getOrderType());

    }
}

