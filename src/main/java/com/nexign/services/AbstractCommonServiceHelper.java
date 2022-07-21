package com.nexign.services;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import static com.nexign.constants.urls.RequestUrl.PROMOCODE_INVENTORY_BOOKING;
import static com.nexign.constants.urls.RequestUrl.getPromoCodeUrl;

public abstract class AbstractCommonServiceHelper {
    public static MultiValueMap getBaseHeadersMap (){ //todo спросить у Андрея  про, мб лучше внедрять через конструктор как бин???
        MultiValueMap<String,String> baseHeaders = new HttpHeaders(); //todo и наверное это лучше вынести в слой сервиса, где будет вся логика, а
        // todo делегейт будет что то типа контроллера
        baseHeaders.add("APPL_CODE","CRAB");
        baseHeaders.add("LOGIN","CRAB");
        return baseHeaders;
    }


    private String getUrl(String url, MultiValueMap<String, String> params) {
        return UriComponentsBuilder.fromUriString(url).queryParams(params).build().toUriString();
    }

}
