package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

public class VacuumWorldPrinter {

    private VacuumWorldPrinter() {}
    
    public static void dumpModel2(Map<VacuumWorldCoordinates, VacuumWorldLocation> model) {
	int size = (int) Math.sqrt(model.size());
	
	String wallLine = buildWallLine(size);
	List<String> rows = buildRows(model, size);
	
	//todo
	String representation = buildRepresentation(wallLine, intermediateLines);
	
	LogUtils.log(representation);
    }
    
    private static List<String> buildRows(Map<VacuumWorldCoordinates, VacuumWorldLocation> model, int size) {
	List<String> lines = new ArrayList<>();
	
	for(int i = 0; i < size; i++) {
	    lines.addAll(buildRow(model, i, size));
	}
	
	return lines;
    }

    private static String buildRow(Map<VacuumWorldCoordinates, VacuumWorldLocation> model, int i, int size) {
	if(i == 0) {
	    return buildFirstRow(model, size);
	}
	else if(i == size -1) {
	    return buildLastRow(model, size);
	}
	else {
	    return buildIntermediateRow(model, i, size);
	}
    }

    private static String buildIntermediateRow(Map<VacuumWorldCoordinates, VacuumWorldLocation> model, int i, int size) {
	StringBuilder builder = new StringBuilder();
	
	builder.append(buildWhiteLine(size));
	builder.append(buildContextualLine(model.entrySet().stream().filter(e -> e.getKey().isYSuch(i)).map(Entry::getValue).collect(Collectors.toList()), size));
	builder.append(buildWhiteLine(size));
	builder.append(buildSeparatorLine(size));
	
	return builder.toString();
    }

    private static String buildLastRow(Map<VacuumWorldCoordinates, VacuumWorldLocation> model, int size) {
	StringBuilder builder = new StringBuilder();
	
	builder.append(buildWhiteLine(size));
	builder.append(buildContextualLine(model.entrySet().stream().filter(e -> e.getKey().isYSuch(size - 1)).map(Entry::getValue).collect(Collectors.toList()), size));
	builder.append(buildWhiteLine(size));
	builder.append(buildWallLine(size));
	
	return builder.toString();
    }

    private static String buildFirstRow(Map<VacuumWorldCoordinates, VacuumWorldLocation> model, int size) {
	StringBuilder builder = new StringBuilder();
	
	builder.append(buildWallLine(size));
	builder.append(buildWhiteLine(size));
	builder.append(buildContextualLine(model.entrySet().stream().filter(e -> e.getKey().isYSuch(0)).map(Entry::getValue).collect(Collectors.toList())));
	builder.append(buildWhiteLine(size));
	builder.append(buildSeparatorLine(size));
	
	return builder.toString();
    }
    
    private static Object buildSeparatorLine(int size) {
	StringBuilder builder = new StringBuilder("#");
	
	for(int i = 0; i < size - 1; i++) {
	    builder.append("++++++++"); //8 characters
	}
	
	builder.append("+++++++#\n"); //7 + 1 + 1 characters
	
	return builder.toString();
    }

    private static String buildContextualLine(List<VacuumWorldLocation> locationsInSpecificRow) {
	StringBuilder builder = new StringBuilder("#");
	
	for(int i = 0; i < locationsInSpecificRow.size() - 1; i++) {
	    builder.append(parseLocation(locationsInSpecificRow.get(i)));
	    builder.append('+');
	}
	
	builder.append(parseLocation(locationsInSpecificRow.get(locationsInSpecificRow.size() - 1)));
	builder.append("#\n");
	
	return builder.toString();
    }

    private static String parseLocation(VacuumWorldLocation vacuumWorldLocation) {
	VacuumWorldLocationAppearance appearance = vacuumWorldLocation.getAppearance();
	
	if(appearance.isAnActiveBodyThere()) {
	    return parseLocationWithActiveBody(appearance);
	}
	else if(appearance.isDirtThere()) {
	    return "   " + appearance.getDirtAppearanceIfAny().getColor().toChar() + "   ";
	}
	else {
	    return "       ";
	}
    }

    private static String parseLocationWithActiveBody(VacuumWorldLocationAppearance appearance) {
	char activeBody = getActiveBodyRepresentation(appearance);
	
	if(appearance.isDirtThere()) {
	    return "  " + activeBody + "+" + appearance.getDirtAppearanceIfAny().getColor().toChar() + "  ";
	}
	else {
	    return "   " + activeBody + "   ";
	}
    }

    private static char getActiveBodyRepresentation(VacuumWorldLocationAppearance appearance) {
	if(appearance.isACleaningAgentThere()) {
	    return appearance.getAgentAppearanceIfAny().getColor().toChar();
	}
	return null; //TODO finish implementation.
    }

    private static String buildWallLine(int length) {
	StringBuilder builder = new StringBuilder(length);
	
	for(int i = 0; i < length; i++) {
	    builder.append('#');
	}
	
	builder.append('\n');
	
	return builder.toString();
    }

    private static String buildWhiteLine(int size) {
	StringBuilder builder = new StringBuilder("#");
	
	for(int i = 0; i < size - 1; i++) {
	    builder.append("       "); //7 characters
	    builder.append('+');
	}
	
	builder.append("       #\n"); //7 + 1 + 1 characters
	
	return builder.toString();
    }
    
    
    
    
    

    
    
    
    
    
    
    
    private static String buildRepresentation(String wallLine, List<String> intermediateLines) {
	StringBuilder builder = new StringBuilder();
	
	builder.append(wallLine);
	intermediateLines.forEach(line -> appendWithNewLine(builder, line));
	builder.append(wallLine);
	
	return builder.toString();
    }

    private static void appendWithNewLine(StringBuilder builder, String line) {
	builder.append(line);
	builder.append('\n');
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void dumpModel(Map<VacuumWorldCoordinates, VacuumWorldLocation> model) {
	int size = (int) Math.sqrt(model.size());
	
	Map<Integer, List<VacuumWorldLocationAppearance>> rows = createRowsMap(model, size);
	List<String> rowsList = createRowsList(rows, size);
	String semifinalRepresentation = polish(rowsList);
	String finalRepresentation = finish(semifinalRepresentation);
	
	LogUtils.log(finalRepresentation);
    }

    private static String finish(String semifinalRepresentation) {
	String[] tokens = semifinalRepresentation.split("\n");
	StringBuilder builder = new StringBuilder();
	
	for(int i = 0; i < tokens.length; i++) {
	    if(i == 0 || i == tokens.length - 1) {
		builder.append(buildWallLine(tokens[i].length()));
	    }
	    else {
		builder.append(tokens[i]);
	    }
	    
	    builder.append('\n');
	}
	
	return builder.toString();
    }

    private static String polish(List<String> rowsList) {
	StringBuilder builder = new StringBuilder();
	rowsList.forEach(builder::append);
	
	return removeUselessBorders(builder.toString());
    }

    private static String removeUselessBorders(String string) {
	String[] tokens = string.split("\n");
	StringBuilder builder = new StringBuilder();
	
	for(int i = 0; i < tokens.length; i++) {
	    if(i == 0 || i == tokens.length - 1 || (i + 1) % 5 != 0) {
		builder.append(simplify(tokens[i]));
		builder.append("\n");
	    }
	}
	
	return builder.toString();
	
    }

    private static Object simplify(String string) {
	Iterable<String> pieces = Splitter.fixedLength(9).split(string);
	StringBuilder builder = new StringBuilder();
	pieces.forEach(p -> builder.append(p.substring(0, p.length() - 1)));
	builder.append('#');
	
	return builder.toString();
    }

    private static List<String> createRowsList(Map<Integer, List<VacuumWorldLocationAppearance>> rows, int size) {
	List<String> rowsList = new ArrayList<>();
	
	for(int j = 0; j < size; j++) {
	    StringBuilder builder = new StringBuilder();
	    builder.append(merge(rows.get(j)));
	    rowsList.add(builder.toString());
	}
	
	return adjust(rowsList);
    }

    private static List<String> adjust(List<String> rowsList) {
	List<String> toReturn = new ArrayList<>();
	rowsList.forEach(r -> toReturn.add(r.replace("\nX", "\n#").replaceFirst("X", "#")));
	
	return toReturn;
    }

    private static String buildWallLine2(int length) {
	StringBuilder builder = new StringBuilder(length);
	
	for(int i = 0; i < length; i++) {
	    builder.append('#');
	}
	
	return builder.toString();
    }

    private static Map<Integer, List<VacuumWorldLocationAppearance>> createRowsMap(Map<VacuumWorldCoordinates, VacuumWorldLocation> model, int size) {
	Map<Integer, List<VacuumWorldLocationAppearance>> rows = new HashMap<>();
	
	for(int j = 0; j < size; j++) {
	    rows.put(j, new ArrayList<>());
	    
	    for(int i = 0; i < size; i++) {
		rows.get(j).add(model.get(new VacuumWorldCoordinates(i, j)).getAppearance());
	    }
	}
	
	return rows;
    }

    private static String merge(List<VacuumWorldLocationAppearance> list) {
	String head = list.remove(0).toString();
	
	while(!list.isEmpty()) {
	    head = merge(head, list.remove(0).toString());
	}
	
	return head;
    }

    private static String merge(String head, String string) {
	String[] tokens = head.split("\n");
	String[] otherTokens = string.split("\n");
	
	StringBuilder builder = new StringBuilder();
	
	for(int i = 0; i < 5; i++) {
	    builder.append(tokens[i]);
	    builder.append(otherTokens[i]);
	    builder.append("\n");
	}
	
	return builder.toString();
    }
}
