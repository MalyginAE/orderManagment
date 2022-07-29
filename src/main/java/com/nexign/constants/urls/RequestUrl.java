package com.nexign.constants.urls;

import com.nexign.config.properties.*;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
@Component
@RequiredArgsConstructor
public class RequestUrl {
    public static final String PARTNER_SERVICES_URL = "/api/partner/uni-pmp/v1/call";
    public static final String MULTIMAPPER_QUERY_PATH = "/api/catalog/ng-fpc-mmapperapp/v1/conversions";
    public static final String PRODUCT_INVENTORY_URL = "/api/product/product-inventory-cache/v1/";
    public static final String PAYMENT_SERVICE_URL = "/api/service/pay-srv-autopayapp/v1/products/";
    public static final String PROMOCODE_INVENTORY_BOOKING = "/promoTicket/assign";
    public static final String PROMOCODE_INVENTORY_ACTIVATE = "/promoTicket/updateSingle";

    private final BssProperties bssProperties;
    private final OAPIProperties oapiProperties;
    private final PartnerServiceProperties partnerServiceProperties;
    private final PromoCodeInventoryProperties promoCodeInventoryProperties;
    private final SsoProperties ssoProperties;
    private final VaspProperties vaspProperties;




    public  String getPromoCodeUrl(String action){
        String url =String.format("http://%s/api/partner/promocode-inventory-app/v2%s",promoCodeInventoryProperties.getDomain(),action);
        return getUrl(url,getSsoQueryMap());
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

    public String getVaspUrl(String action, String msisdn, String technicalId) {
        String vaspDomain = vaspProperties.getDomain();
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

    public static MultiValueMap getSsoQueryMap(){
        MultiValueMap<String,String> ssoQueryPrams = new HttpHeaders();
        ssoQueryPrams.add("APP1_CODE","CRAB");
        ssoQueryPrams.add("LOGIN","CRAB");
        return ssoQueryPrams;
    }
    public  String getBssActivateUrl(MultisubscriptionOrderParameters parameters, String correlationId) {
        String domain = bssProperties.getDomain();
        MultiValueMap<String,String> queryParams = getSsoQueryMap();
        queryParams.add("correlationId",correlationId);
        queryParams.add("replyTo",bssProperties.getAmqpCallback());
        String url =  String.format("http://%s/openapi/v2/subscribers/msisdn:%s/productOfferings/activate/bulk", domain,parameters.getRelatedParties().getMsisdn());

        return getUrl(url,queryParams);
        }

    public static String getBssCheckProductUrl(MultisubscriptionOrderParameters parameters){
        String domain = "localhost:3000";
        String msisdn = String.valueOf(parameters.getRelatedParties().getContactMsisdn());
        String url = String.format("http://%s/openapi/v2/subscribers/msisdn:%s/products/search",domain,msisdn);
        return getUrl(url, getSsoQueryMap());
    }
}
