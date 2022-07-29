package com.nexign.utils.parsing.ng_psi;

import com.nexign.dto.ng_psi.ProductInventoryFabricRef;
import com.nexign.dto.ng_psi.ProductInventoryProductItem;
import com.nexign.dto.order.context.MultisubscriptionAdditionalMappingContext;
import com.nexign.dto.order.context.MultisubscriptionComponentOrderParameter;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductInventoryPrepareRequestBodyListUtil {
    public static List<ProductInventoryProductItem> setListProducts(MultisubscriptionOrderParameters parameters) {
        List<ProductInventoryProductItem> productList = new ArrayList();
        for (MultisubscriptionComponentOrderParameter c : parameters.getAffectedComponentOrders()) {
            List<ProductInventoryFabricRef> fabricRefs = new ArrayList<>();
            List<MultisubscriptionAdditionalMappingContext> contextList = parameters.getProductContextMap(c.getProductOfferingId());
            for (MultisubscriptionAdditionalMappingContext m : contextList) {
                fabricRefs.add(new ProductInventoryFabricRef(
                        m.getFabricRefId(),
                        m.getFabricProductOfferingId(),
                        m.getFabricProductId()
                ));
            }
            productList.add( ProductInventoryProductItem.builder().
                    productId(c.getInstance().getInstanceId()).
                    isBundle(c.getType().equals("bundle")).
                    characteristic((c.getType().equals("bundle") && Objects.nonNull(parameters.getPromoCodeData())) ? List.of(
                            parameters.getPromoCodeData() ) :  null).
                    productPrice((Objects.nonNull(parameters.getProductPriceData())
                            && parameters.getProductPriceData().getMainComponentOrderId().equals(c.getComponentOrderId()))
                            ? parameters.getProductPriceData().getPriceValue() : null).fabricRef(fabricRefs).
                    build());

        }
        return productList;
    }
}
