package com.nexign;

import com.nexign.annotations.TestIncludedInStatisticCoverage;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.act.ReceiveTaskAction;
import org.camunda.bpm.scenario.act.UserTaskAction;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.camunda.bpm.scenario.Scenario.run;
import static org.mockito.Mockito.*;
@TestIncludedInStatisticCoverage
public class ScenarioTest {
    //ProcessScenario insuranceApplication = mock(ProcessScenario.class);

    @Mock
    private ProcessScenario scenario;
//
//    @BeforeEach
//    public void defineHappyScenario() {
////        when(insuranceApplication.waitsAtUserTask("UserTaskDecideAboutApplication")).thenReturn((task) ->
////                task.complete()
////        );
////         MockitoAnnotations.openMocks(this);
//    }
    @Test
    public void testHappyPath() {

        when(scenario.waitsAtReceiveTask("Activity_03zkxvi")).thenReturn(x ->x.receive());
        when(scenario.waitsAtUserTask("Activity_1y9p7i3")).thenReturn(x -> x.complete());
        run(scenario).startByKey("Process_08635v9").execute();
        verify(scenario).hasCompleted("Activity_03zkxvi");
    }
}
