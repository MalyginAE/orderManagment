package com.nexign.services.bss;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.bss.ActionParameter;
import com.nexign.dto.bss.CcmProductOfferingBulcActivateResponceBodyDto;
import com.nexign.dto.bss.ProductOfferingBulkActivateRequestBodyDto;
import com.nexign.dto.bss.ProductOfferingsBulkActivateParameter;
import com.nexign.dto.common.FabricActionMap;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.nexign.constants.urls.RequestUrl.getBssUrl;


@Service
@RequiredArgsConstructor
public class CcmProductOfferingBulkActivateService {
    private final WebClient webClient;


    public CcmProductOfferingBulcActivateResponceBodyDto createRequest(DelegateExecution execution){
        List<String> technicalId = (List<String>) execution.getVariable(OrderContextConstants.BSS_TECHNICAL_ID);
        ProductOfferingBulkActivateRequestBodyDto bodyDto = prepareRequestBody(technicalId);
        return prepareRequestToActivate(bodyDto,execution).block();
// TODO: 27.07.2022
//LOGIN=${oapiLogin}&APPL_CODE=${applCode}&correlationId=${correlationId}&replyTo=${replyTo}



    }

    private void includeActionMapInContext(DelegateExecution execution){
        List<FabricActionMap> actionMap = (List<FabricActionMap>) execution.getVariable(OrderContextConstants.BSS_ACTION_MAP);
        actionMap = (actionMap == null) ? new ArrayList<>() : actionMap;
        List<String> technicalId = (List<String>) execution.getVariable(OrderContextConstants.BSS_TECHNICAL_ID);
        String correlationId = String.format("%s_%s_%s",execution.getProcessInstanceId(),"startEvent","activate");
        for (String s: technicalId) {
            actionMap.add(new FabricActionMap(s, correlationId, "PENDING_ACTIVATE"));
        }
    }

    private Mono<CcmProductOfferingBulcActivateResponceBodyDto> prepareRequestToActivate(ProductOfferingBulkActivateRequestBodyDto model,DelegateExecution execution) {
        MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) execution.getVariable(OrderContextConstants.ORDER_PARAMETERS);
        return webClient.post().
                uri(getBssUrl(parameters)).
                accept(MediaType.APPLICATION_JSON).
                bodyValue(model).
                exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK) || response.statusCode().equals(HttpStatus.ACCEPTED)) {
                        execution.setVariableLocal("httpAnswerCode",response.statusCode());
                        return response.bodyToMono(CcmProductOfferingBulcActivateResponceBodyDto.class);
                    } else {
                        return Mono.error((Supplier<? extends Throwable>) response.createException());
                    }
                })
                .retryWhen(Retry.fixedDelay(4, Duration.ofSeconds(5)));
    }






    private ProductOfferingBulkActivateRequestBodyDto prepareRequestBody(List<String> bssTechnicalIds){
        List<ProductOfferingsBulkActivateParameter> parameters = new ArrayList<>();
        bssTechnicalIds.forEach(bssTechnicalId ->
                parameters.add(new ProductOfferingsBulkActivateParameter(
                        Long.getLong(bssTechnicalId),
                        new ActionParameter(true)
                ))
        );
        return new ProductOfferingBulkActivateRequestBodyDto(parameters);
    }

/**
 *   void process(Exchange exchange) throws Exception {
 *         String waitBSSTimeout = zooService.getString(ZooKeeperParameters.BSS_WAIT_TIMEOUT)
 *         Integer httpAnswerCode = exchange.in.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer)
 *         exchange.setProperty("httpAnswerCode", httpAnswerCode)
 *         if (httpAnswerCode == 200 || httpAnswerCode == 202) {
 *             def answerBody = mapper.readValue(exchange.in.getBody(String), CcmProductOfferingBulkActivateAnswer)
 *             if (answerBody.orderId != null) {
 *                 exchange.setProperty("ps.crab.wf.context.CheckBssOrderId", true)
 *                 logger.debug("Creating bssOrderId = ${answerBody.orderId}")
 *
 *                 String correlationId = exchange.getProperty(CommonScenarioHelper.CONTEXT_CORRELATION_ID, String)
 *                 exchange.in.setHeader(CommonScenarioHelper.HEADER_WAIT_TIMEOUT, waitBSSTimeout)
 *                 exchange.in.setHeader(CommonScenarioHelper.HEADER_WAIT_CORRELATION_ID, correlationId)
 *
 *                 CommonScenarioHelper.prepareStatement(exchange, "DONE", mapper.valueToTree(answerBody))
 *             } else {
 *                 String correlationId = exchange.getProperty(CommonScenarioHelper.CONTEXT_CORRELATION_ID, String)
 *                 Boolean bssCheckCase = correlationId.contains("_ccm_product_deactivate")
 *                 exchange.setProperty("ps.crab.wf.context.CheckBssOrderId", bssCheckCase)
 *                 CommonScenarioHelper.prepareStatement(exchange, "DONE", "BssOrder is null")
 *             }
 *         } else {
 *             String status = (httpAnswerCode == 500) ? "FAIL" : "ERROR"
 *             logger.debug("${status}:{}", mapper.valueToTree(exchange.in.getBody(String)))
 *
 *             CommonScenarioHelper.prepareErrorModel(exchange, "BSS")
 *
 *             exchange.setProperty("ps.crab.wf.context.rollbackVasp", true)
 *
 *             CommonScenarioHelper.prepareStatement(exchange, status, exchange.in.getBody(String))
 *         }
 *
 *
 *
 *
 *         ////
 *
 *         package com.nexign.bss.tailored_crab_mf_ml.scenarios.ccm
 *
 *
 * import static org.apache.http.entity.ContentType.APPLICATION_JSON
 *
 * class CcmAnalyzeResposeCodeRouteBuilder extends RouteBuilder {
 *     private static final ZooService zooService = CommonScenarioHelper.initializeZooService()
 *     private static Logger logger = LoggerFactory.getLogger(CcmAnalyzeResposeCodeRouteBuilder.class)
 *     private static ObjectMapper mapper = CommonScenarioHelper.initializeObjectMapper()
 *     @Override
 *     void configure() throws Exception {
 *         from("direct:wfCcmAnalyzeResponseCode")
 *                 .choice()
 *                 .when({ Exchange ex ->
 *                     return ex.getProperty("httpAnswerCode") == 202
 *                 })
 *                 .to("crab:wait")
 *                 .endChoice()
 *                 .when({ Exchange ex ->
 *                     return ex.getProperty("httpAnswerCode") == 200 && ex.getProperty("ps.crab.wf.context.CheckBssOrderId", Boolean)
 *                 })
 *                 .to("crab:success")
 *                 .endChoice()
 *                 .when({ Exchange ex ->
 *                     return ex.getProperty("httpAnswerCode") == 200 && !ex.getProperty("ps.crab.wf.context.CheckBssOrderId",Boolean)
 *                 })
 *                 .to("direct:wfBssCheckProductOfferingId")
 *                 .endChoice()
 *                 .otherwise()
 *                 .to("crab:error")
 *                 .endChoice()
 *                 .end()
 *
 *         from("direct:wfBssCheckProductOfferingId")
 *                 .process({ Exchange ex ->
 *                     logger.debug("check invoked")
 *                     String oapiUrl = zooService.getString(ZooKeeperParameters.OAPI_URL)
 *                     String oapiLogin = zooService.getString(ZooKeeperParameters.OAPI_LOGIN)
 *                     String applCode = zooService.getString(ZooKeeperParameters.OAPI_APPL_CODE)
 *
 *                     MultisubscriptionOrderParameters parameters = ex.getProperty("ps.crab.wf.context.inputOrderParameters", MultisubscriptionOrderParameters)
 *                     List<String> bssTechnicalId = ex.getProperty("ps.crab.wf.context.bssTechnicalId", ArrayList.class)
 *                     def msisdn = parameters.relatedParties.contactMsisdn
 *                     String url = "http://$oapiUrl/openapi/v2/subscribers/msisdn:${msisdn}/products/search"
 *                     String query = "LOGIN=${oapiLogin}&APPL_CODE=${applCode}"
 *
 *                     def requestBody = JsonOutput.toJson([productOfferingIds:bssTechnicalId,productStatusIds:[1,2]])
 *                     logger.debug("${HttpMethods.POST} ${Exchange.HTTP_URI}=${url}, ${Exchange.HTTP_QUERY}=${query}, body:${mapper.valueToTree(requestBody)}")
 *
 *                     ex.in.setHeader(Exchange.ACCEPT_CONTENT_TYPE, APPLICATION_JSON)
 *                     ex.in.setHeader(Exchange.CONTENT_TYPE, APPLICATION_JSON)
 *                     ex.in.setHeader(Exchange.HTTP_URI, url)
 *                     ex.in.setHeader(Exchange.HTTP_QUERY, query)
 *                     ex.in.setHeader(Exchange.HTTP_METHOD, HttpMethods.POST)
 *                     ex.in.setBody(requestBody)
 *                 })
 *                 .to("http4://singleEntryPoint")
 *                 .process({ Exchange ex ->
 *                     Integer httpAnswerCode = ex.in.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer)
 *                     ex.setProperty("answerHttpCode", httpAnswerCode)
 *                     MultisubscriptionOrderParameters parameters = ex.getProperty("ps.crab.wf.context.inputOrderParameters", MultisubscriptionOrderParameters)
 *                     List<String> bssTechnicalId = ex.getProperty("ps.crab.wf.context.bssTechnicalId", ArrayList.class)
 *                     def answerBody = mapper.readValue(ex.in.getBody(String), BssCheckResponseAnswer)
 *                     if (httpAnswerCode == 200) {
 *                         if (bssTechnicalId.size() == answerBody.listInfo.count) {
 *                             for (BssCheckItems e : answerBody.items) {
 *                                 parameters.addedProviderInstanceIdInContextMap(e.productOfferingId, e.productId)
 *                             }
 *                             ex.setProperty("ps.crab.wf.context.inputOrderParameters", parameters)
 *                             CommonScenarioHelper.prepareStatement(ex, "DONE", mapper.valueToTree(answerBody))
 *                         } else if (bssTechnicalId.size() != answerBody.listInfo.count) {
 *                             ex.setProperty("ps.crab.wf.context.rollbackVasp", true)
 *                             CommonScenarioHelper.prepareStatement(ex, "ERROR", "Missing productOfferingIds")
 *                         }
 *                     } else {
 *                         String status = (httpAnswerCode == 500) ? "FAIL" : "ERROR"
 *                         CommonScenarioHelper.prepareErrorModel(ex, "BSS")
 *                         ex.setProperty("ps.crab.wf.context.rollbackVasp", true)
 *                         CommonScenarioHelper.prepareStatement(ex, status, mapper.valueToTree(answerBody))
 *                     }
 *                 }
 *                 )
 *
 *
 *     }
 * }
 */
}
