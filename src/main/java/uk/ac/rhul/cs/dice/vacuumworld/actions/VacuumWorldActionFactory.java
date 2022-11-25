package uk.ac.rhul.cs.dice.vacuumworld.actions;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldCommunicativeActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldSensingActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;

public class VacuumWorldActionFactory {

    private VacuumWorldActionFactory() {}

    public static VacuumWorldAbstractAction generate(Enum<?> code, Object... additional) {
        if (VacuumWorldPhysicalActionsEnum.class.isAssignableFrom(code.getClass())) {
            return generatePhysical((VacuumWorldPhysicalActionsEnum) code);
        }
        else if (VacuumWorldSensingActionsEnum.class.isAssignableFrom(code.getClass())) {
            return generateSensing((VacuumWorldSensingActionsEnum) code);
        }
        else if (VacuumWorldCommunicativeActionsEnum.class.isAssignableFrom(code.getClass())) {
            return generateCommunicative((VacuumWorldCommunicativeActionsEnum) code, additional);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private static VacuumWorldAbstractAction generatePhysical(VacuumWorldPhysicalActionsEnum code) {
        switch (code) {
            case MOVE:
                return new VacuumWorldMoveAction();
            case TURN_LEFT:
            case TURN_RIGHT:
                return VacuumWorldTurnAction.generate(TurnDirection.fromActionType(code));
            case CLEAN:
                return new VacuumWorldCleanAction();
            default:
                return attemptCreationOfUserActions(code);
        }
    }

    private static VacuumWorldAbstractAction attemptCreationOfUserActions(VacuumWorldPhysicalActionsEnum code) {
        if (VacuumWorldPhysicalActionsEnum.DROP_DIRT.equals(code)) {
            return new VacuumWorldDropDirtAction(); // i.e., random color.
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private static VacuumWorldAbstractAction generateSensing(VacuumWorldSensingActionsEnum code) {
        if (VacuumWorldSensingActionsEnum.STAY_IDLE.equals(code)) {
            return new VacuumWorldIdleAction();
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private static VacuumWorldAbstractAction generateCommunicative(VacuumWorldCommunicativeActionsEnum code, Object[] additional) {
        switch (code) {
            case SPEAK:
                return new VacuumWorldSpeakAction(parseMessage(additional), parseSender(additional),
                        parseRecipients(additional));
            case BROADCAST:
                return new VacuumWorldBroadcastingAction(parseMessage(additional), parseSender(additional));
            default:
                throw new IllegalArgumentException();

        }
    }

    private static String parseSender(Object[] additional) {
        checkArgumentsLength(additional, 2);
        Object o = additional[1];

        return o instanceof String ? (String) o : "__UNKNOWN_SENDER__";
    }

    private static VacuumWorldMessage parseMessage(Object[] additional) {
        checkArgumentsLength(additional, 1);
        Object o = additional[0];

        return o instanceof String ? new VacuumWorldMessage((String) o) : new VacuumWorldMessage();
    }

    private static Set<String> parseRecipients(Object[] additional) {
        checkArgumentsLength(additional, 3);

        Object o = additional[2];

        return o instanceof Set<?> ? parseRecipients((Set<?>) o) : Collections.emptySet();
    }

    private static Set<String> parseRecipients(Set<?> candidates) {
        return candidates.stream().filter(candidate -> String.class.isAssignableFrom(candidate.getClass())).map(String.class::cast).collect(Collectors.toSet());
    }

    private static void checkArgumentsLength(Object[] additional, int minimumLength) {
        if (additional.length < minimumLength) {
            throw new IllegalArgumentException();
        }
    }
}
