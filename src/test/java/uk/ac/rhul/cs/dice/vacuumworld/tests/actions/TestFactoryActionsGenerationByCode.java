package uk.ac.rhul.cs.dice.vacuumworld.tests.actions;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.rhul.cs.dice.agentcommon.utils.LogUtils;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldBroadcastingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldDropDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSpeakAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;

public class TestFactoryActionsGenerationByCode {
    
    @Test
    public void testMoveAction() {
	test('M', VacuumWorldMoveAction.class);
    }
    
    @Test
    public void testTurnLeftAction() {
	test('L', VacuumWorldTurnLeftAction.class);
    }
    
    @Test
    public void testTurnRightAction() {
	test('R', VacuumWorldTurnRightAction.class);
    }
    
    @Test
    public void testCleanAction() {
	test('C', VacuumWorldCleanAction.class);
    }
    
    @Test
    public void testDropDirtAction() {
	test('D', VacuumWorldDropDirtAction.class);
    }
    
    @Test
    public void testSensingAction() {
	test('S', VacuumWorldSensingAction.class);
    }
    
    @Test
    public void testSpeakAction() {
	test('T', VacuumWorldSpeakAction.class, new VacuumWorldMessage("Hello World"), new HashSet<>(Arrays.asList("A1", "A2")));
    }
    
    @Test
    public void testBroadcastingAction() {
	test('B', VacuumWorldBroadcastingAction.class, new VacuumWorldMessage("Hello World"));
    }
    
    private void test(char code, Class<?> benchmark, Object... additional) {
	LogUtils.log("Testing action creation from factory with code \"" + code + "\" ...");
	LogUtils.log("Expecting " + benchmark.getSimpleName() + " (" + benchmark.getName() + ") ...");
	
	VacuumWorldAbstractAction action = VacuumWorldAbstractAction.generate(code, additional);
	
	LogUtils.log("Got " + action.getClass().getSimpleName() + " (" + action.getClass().getName() + ") --> " + getMatch(action, benchmark));
	Assert.assertTrue("Expecting " + benchmark.getSimpleName() + " (" + benchmark.getName() + "), got " + action.getClass().getSimpleName() + " (" + action.getClass().getName() + ").",  benchmark.isInstance(action));
    }

    private String getMatch(VacuumWorldAbstractAction action, Class<?> benchmark) {
	return (benchmark.isInstance(action) ? "OK" : "KO") + "\n";
    }
}