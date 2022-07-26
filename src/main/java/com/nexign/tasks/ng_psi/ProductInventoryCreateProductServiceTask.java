package com.nexign.tasks.ng_psi;

import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.ng_psi.ProductInventoryCreateProductService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductInventoryCreateProductServiceTask extends AbstractDelegate {
    private final ProductInventoryCreateProductService productService;
    @Override
    public void run(DelegateExecution delegateExecution) {

    }
}
