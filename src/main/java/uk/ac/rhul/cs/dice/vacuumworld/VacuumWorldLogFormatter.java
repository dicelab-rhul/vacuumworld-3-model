package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class VacuumWorldLogFormatter extends SimpleFormatter {

    @Override
    public String format(LogRecord record) {
	return record.getMessage() + "\n";
    }
}