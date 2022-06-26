package com.nexign;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.camunda.bpm.extension.process_test_coverage.spring_test.ProcessEngineCoverageConfiguration;
import org.camunda.bpm.extension.process_test_coverage.spring_test.ProcessEngineCoverageTestExecutionListener;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.managementService;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest

@Import({CoverageTestConfiguration.class, ProcessEngineCoverageConfiguration.class})
@TestExecutionListeners(value = ProcessEngineCoverageTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)

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
    System.out.println("JOBS: " + managementService().createJobQuery().list());
    BpmnAwareTests.execute(BpmnAwareTests.job());
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
