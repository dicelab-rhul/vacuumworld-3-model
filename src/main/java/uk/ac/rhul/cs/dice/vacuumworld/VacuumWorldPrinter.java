package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

public class VacuumWorldPrinter {

    private VacuumWorldPrinter() {}
    
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

    private static String buildWallLine(int length) {
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