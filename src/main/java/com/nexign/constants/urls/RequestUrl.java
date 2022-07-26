package com.nexign.constants.urls;

public class RequestUrl {
    public static final String PARTNER_SERVICES_URL = "/api/partner/uni-pmp/v1/call";
    public static final String MULTIMAPPER_QUERY_PATH = "/api/catalog/ng-fpc-mmapperapp/v1/conversions";
    public static final String PRODUCT_INVENTORY_URL = "/api/product/product-inventory-cache/v1/";
    public static final String PAYMENT_SERVICE_URL = "/api/service/pay-srv-autopayapp/v1/products/";
    public static final String PROMOCODE_INVENTORY_BOOKING = "/promoTicket/assign";
    public static final String PROMOCODE_INVENTORY_ACTIVATE = "/promoTicket/updateSingle";

    public static String getPromoCodeUrl(String action){
        return "http://localhost:3333/api/partner/promocode-inventory-app/v2".concat(action);
    }

    public static String getMultimapperUrl(){
        return "http://localhost:3333".concat(MULTIMAPPER_QUERY_PATH);
    }

    public static String getProductInventoryUrl(String event){
        return "http://localhost:3333".concat(PRODUCT_INVENTORY_URL).concat(event);
    }
}
