package uk.ac.rhul.cs.dice.vacuumworld.tests;

import org.junit.runner.JUnitCore;

import uk.ac.rhul.cs.dice.agentcommon.utils.LogUtils;
import uk.ac.rhul.cs.dice.vacuumworld.tests.actions.TestFactoryActionsGenerationByCode;

public class TestMain {

    private TestMain() {}

    public static void main(String[] args) {
	JUnitCore.runClasses(TestFactoryActionsGenerationByCode.class).getFailures().forEach(LogUtils::log);
    }
}