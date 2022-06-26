//package com.nexign;
//
//import org.camunda.bpm.scenario.ProcessScenario;
//import org.camunda.bpm.scenario.Scenario;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.camunda.bpm.scenario.Scenario.run;
//import static org.mockito.Mockito.*;
//
//public class ScenarioTest {
//    @Autowired
//    ProcessScenario process = mock(ProcessScenario.class);
//
//    @Test
//    public void scenarioCase() {
//
//        // "given" part of the test
//        when(process.waitsAtUserTask("CompleteWork")).thenReturn(
//                (task) -> task.complete()
//        );
//        // "when" part of the test
//        Scenario scenario = Scenario.run(process).startByKey("Process_0345ai9")
//               // either just start process by key ...
//                .execute();
//       // run(process).startByKey("Process_0345ai9").execute();
//        // "then" part of the test
//        verify(process).hasFinished("WorkFinished");
//
//    }
//}
