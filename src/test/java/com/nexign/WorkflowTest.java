package com.nexign;

import com.nexign.annotations.TestIncludedInStatisticCoverage;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@TestIncludedInStatisticCoverage
public class WorkflowTest {
  private static final String processDefinitionKey = "OrderManagement-process";

  @Autowired
  public RuntimeService runtimeService;

  @Test
  public void shouldExecuteHappyPath() {
    // given
    ProcessInstance processInstance =runtimeService.startProcessInstanceByKey(processDefinitionKey);;

    // when

    // then
   //assertTrue(true);


    assertThat(processInstance).isStarted();
    assertThat(processInstance).isWaitingAtExactly("Activity_0juc6su","Activity_1pzxjaq");
    //System.out.println("JOBS: " + managementService().createJobQuery().list());
    assertThat(processInstance).isWaitingFor("RabbitCallBack");
   // assertThat(processInstance).
    // System.out.println(processInstance.get);
    System.out.println("JOBS: " + managementService().createJobQuery().active().list());
     BpmnAwareTests.execute(BpmnAwareTests.job());
     assertThat(processInstance).task().hasDefinitionKey("Activity_0zdbcyb");
     complete(task(processInstance));
     assertThat(processInstance).isEnded();

//

    //BpmnAwareTests.assertThat(processInstance).;
    System.out.println("JOBS: " + managementService().createJobQuery().active().list());
    System.out.println("JOBS: " + managementService().createJobQuery().list());

//    BpmnAwareTests.execute(BpmnAwareTests.jobQuery().processInstanceId(processInstance.getId()).list().get(0));
//
//    BpmnAwareTests.assertThat(processInstance).hasPassed("Activity_0gqoxk1");
//    BpmnAwareTests.assertThat(processInstance).isEnded();
//            .task()
//            .hasDefinitionKey("say-hello")
//            .hasCandidateUser("andrey")
//            .isNotAssigned();
  }

  @Test
  public void processIsActive(){
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
    assertThat(processInstance).isActive();
  }



}
