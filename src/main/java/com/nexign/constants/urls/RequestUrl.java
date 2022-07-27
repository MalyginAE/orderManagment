package com.nexign.constants.urls;

import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

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

    public static String getPartnerServiceUrl(){
        return PARTNER_SERVICES_URL;
    }

    public String getUrl(String url, MultiValueMap<String, String> params) {
        return UriComponentsBuilder.fromUriString(url).queryParams(params).build().toUriString();
    }

    public static MultiValueMap getBaseHeadersMap (){
        MultiValueMap<String,String> baseHeaders = new HttpHeaders();
        baseHeaders.add("APPL_CODE","CRAB");
        baseHeaders.add("LOGIN","CRAB");
        return baseHeaders;
    }
    public static String getBssUrl(MultisubscriptionOrderParameters parameters) {
        return String.format("%s/openapi/v2/subscribers/msisdn:%s/productOfferings/activate/bulk", "http://localhost:3333",parameters.getRelatedParties().getMsisdn());
    }
}
