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

    public static String getVaspUrl(String action, String msisdn, String technicalId) {
        String vaspDomain = "localhost:3000";
        MultiValueMap<String,String> queryParams = new HttpHeaders();
        queryParams.add("serviceid",technicalId);
        queryParams.add("msisdn",msisdn);
        queryParams.add("status",action);

        String url = String.format("http://%s/cpapsm/api/cp/v1/subscription/",vaspDomain);

        return getUrl(url,queryParams);
    }

    public static String getUrl(String url, MultiValueMap<String, String> params) {
        return UriComponentsBuilder.fromUriString(url).queryParams(params).build().toUriString();
    }

    public static MultiValueMap getBaseQueryMap (){
        MultiValueMap<String,String> ssoQueryPrams = new HttpHeaders();
        ssoQueryPrams.add("APPL_CODE","CRAB");
        ssoQueryPrams.add("LOGIN","CRAB");
        return ssoQueryPrams;
    }
    public static String getBssActivateUrl(MultisubscriptionOrderParameters parameters) {
        return String.format("%s/openapi/v2/subscribers/msisdn:%s/productOfferings/activate/bulk", "http://localhost:3333",parameters.getRelatedParties().getMsisdn());
    }

    public static String getBssCheckProductUrl(MultisubscriptionOrderParameters parameters){
        String msisdn = String.valueOf(parameters.getRelatedParties().getContactMsisdn());
        String url = "http://%s/openapi/v2/subscribers/msisdn:%s/products/search";
        //String query = "LOGIN=${oapiLogin}&APPL_CODE=${applCode}"
        return url;
    }
}
