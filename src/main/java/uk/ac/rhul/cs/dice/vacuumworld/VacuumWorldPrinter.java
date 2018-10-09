package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.cloudstrife9999.logutilities.LogUtils;

import com.google.common.collect.ImmutableMap;

import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

public class VacuumWorldPrinter {

    private VacuumWorldPrinter() {}

    public static void dumpModelFromLocations(Map<VacuumWorldCoordinates, VacuumWorldLocation> model) {
	Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> newModel = new HashMap<>();
	model.entrySet().forEach(e -> newModel.put(e.getKey(), e.getValue().getAppearance()));

	dumpModelFromLocationAppearances(ImmutableMap.copyOf(newModel));

    }

    public static void dumpModelFromLocationAppearances(Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> model) {
	int size = (int) Math.sqrt(model.size());
	String representation = size == 1 ? getSingleLocationRepresentation(model) : buildRows(model, size);

	LogUtils.log("\n" + representation);
    }

    private static String getSingleLocationRepresentation(Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> model) {
	return model.get(new VacuumWorldCoordinates(0, 0)).toString();
    }

    private static String buildRows(Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> model, int size) {
	StringBuilder builder = new StringBuilder();

	for (int i = 0; i < size; i++) {
	    builder.append(buildRow(model, i, size));
	}

	return builder.toString();
    }

    private static String buildRow(Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> model, int i, int size) {
	if (i == 0) {
	    return buildFirstRow(model, size);
	}
	else if (i == size - 1) {
	    return buildLastRow(model, size);
	}
	else {
	    return buildIntermediateRow(model, i, size);
	}
    }

    private static String buildIntermediateRow(Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> model, int i, int size) {
	StringBuilder builder = new StringBuilder();

	builder.append(buildWhiteLine(size));
	builder.append(buildContextualLine(model.entrySet().stream().filter(e -> e.getKey().isYSuch(i)).map(Entry::getValue).sorted().collect(Collectors.toList())));
	builder.append(buildWhiteLine(size));
	builder.append(buildSeparatorLine(size));

	return builder.toString();
    }

    private static String buildLastRow(Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> model, int size) {
	StringBuilder builder = new StringBuilder();

	builder.append(buildWhiteLine(size));
	builder.append(buildContextualLine(model.entrySet().stream().filter(e -> e.getKey().isYSuch(size - 1)).map(Entry::getValue).sorted().collect(Collectors.toList())));
	builder.append(buildWhiteLine(size));
	builder.append(buildWallLine(size));

	return builder.toString();
    }

    private static String buildFirstRow(Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> model, int size) {
	StringBuilder builder = new StringBuilder();

	builder.append(buildWallLine(size));
	builder.append(buildWhiteLine(size));
	builder.append(buildContextualLine(model.entrySet().stream().filter(e -> e.getKey().isYSuch(0)).map(Entry::getValue).sorted().collect(Collectors.toList())));
	builder.append(buildWhiteLine(size));
	builder.append(buildSeparatorLine(size));

	return builder.toString();
    }

    private static Object buildSeparatorLine(int size) {
	StringBuilder builder = new StringBuilder("#");

	for (int i = 0; i < size - 1; i++) {
	    builder.append("++++++++"); // 8 characters
	}

	builder.append("+++++++#\n"); // 7 + 1 + 1 characters

	return builder.toString();
    }

    private static String buildContextualLine(List<VacuumWorldLocationAppearance> locationsInSpecificRow) {
	StringBuilder builder = new StringBuilder("#");

	for (int i = 0; i < locationsInSpecificRow.size() - 1; i++) {
	    builder.append(parseLocation(locationsInSpecificRow.get(i)));
	    builder.append('+');
	}

	builder.append(parseLocation(locationsInSpecificRow.get(locationsInSpecificRow.size() - 1)));
	builder.append("#\n");

	return builder.toString();
    }

    private static String parseLocation(VacuumWorldLocationAppearance location) {

	if (location.isAnActiveBodyThere()) {
	    return parseLocationWithActiveBody(location);
	}
	else if (location.isDirtThere()) {
	    return "   " + location.getDirtAppearanceIfAny().getColor().toChar() + "   ";
	}
	else {
	    return "       ";
	}
    }

    private static String parseLocationWithActiveBody(VacuumWorldLocationAppearance appearance) {
	char activeBody = getActiveBodyRepresentation(appearance);

	if (appearance.isDirtThere()) {
	    return "  " + activeBody + "+" + appearance.getDirtAppearanceIfAny().getColor().toChar() + "  ";
	}
	else {
	    return "   " + activeBody + "   ";
	}
    }

    private static char getActiveBodyRepresentation(VacuumWorldLocationAppearance appearance) {
	if (appearance.isACleaningAgentThere()) {
	    return appearance.getAgentAppearanceIfAny().getColor().toChar();
	}
	else if (appearance.isAUserThere()) {
	    return ActorType.USER.toChar();
	}
	else if (appearance.isAnAvatarThere()) {
	    return ActorType.AVATAR.toChar();
	}
	else {
	    throw new IllegalArgumentException();
	}
    }

    private static String buildWallLine(int size) {
	int length = 9 + 8 * (size - 1);
	StringBuilder builder = new StringBuilder(length);

	for (int i = 0; i < length; i++) {
	    builder.append('#');
	}

	builder.append('\n');

	return builder.toString();
    }

    private static String buildWhiteLine(int size) {
	StringBuilder builder = new StringBuilder("#");

	for (int i = 0; i < size - 1; i++) {
	    builder.append("       "); // 7 characters
	    builder.append('+');
	}

	builder.append("       #\n"); // 7 + 1 + 1 characters

	return builder.toString();
    }
}