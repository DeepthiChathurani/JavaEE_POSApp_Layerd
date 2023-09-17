package lk.ijse.pos.bo;

import lk.ijse.pos.bo.custom.impl.CustomerBOImpl;
import lk.ijse.pos.bo.custom.impl.PurchaseOrderBOImpl;

import java.sql.SQLException;

public class BOFactory {
    private static BOFactory boFactory;

    public BOFactory() {
    }

    public static BOFactory getBoFactory() {
        if(boFactory==null){
            boFactory=new BOFactory();
        }
        return boFactory;
    }
    public enum BoType{
        Customer,Item,PurchaseOrder
    }
    public SuperBO getBoType(BOFactory.BoType boType) throws SQLException {
        switch (boType){
            case Customer:
                return (SuperBO) new CustomerBOImpl();
            case Item:
                return (SuperBO) new ItemBOImpl();
            case PurchaseOrder:
                return (SuperBO) new PurchaseOrderBOImpl();
        }
        return null;
    }
}
