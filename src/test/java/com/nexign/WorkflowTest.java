package com.nexign;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
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

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
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
