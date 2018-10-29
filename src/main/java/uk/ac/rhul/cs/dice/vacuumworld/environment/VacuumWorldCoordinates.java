package uk.ac.rhul.cs.dice.vacuumworld.environment;

import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractCoordinates;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Coordinates;

public class VacuumWorldCoordinates extends AbstractCoordinates {
    private static final long serialVersionUID = 3580947018632549487L;
    private static final VacuumWorldCoordinates origin = new VacuumWorldCoordinates(0, 0);

    public VacuumWorldCoordinates(int x, int y) {
	super(x, y);
    }

    public VacuumWorldCoordinates(Coordinates toCopy) {
	super(toCopy);
    }
    
    @Override
    public VacuumWorldCoordinates getBackCoordinates(Orientation orientation) {
        return (VacuumWorldCoordinates) super.getBackCoordinates(orientation);
    }
    
    @Override
    public VacuumWorldCoordinates getBackLeftCoordinates(Orientation orientation) {
        return (VacuumWorldCoordinates) super.getBackLeftCoordinates(orientation);
    }
    
    @Override
    public VacuumWorldCoordinates getBackRightCoordinates(Orientation orientation) {
        return (VacuumWorldCoordinates) super.getBackRightCoordinates(orientation);
    }
    
    @Override
    public VacuumWorldCoordinates getEasternCoordinates() {
        return (VacuumWorldCoordinates) super.getEasternCoordinates();
    }
    
    @Override
    public VacuumWorldCoordinates getForwardCoordinates(Orientation orientation) {
        return (VacuumWorldCoordinates) super.getForwardCoordinates(orientation);
    }
    
    @Override
    public VacuumWorldCoordinates getForwardLeftCoordinates(Orientation orientation) {
        return (VacuumWorldCoordinates) super.getForwardLeftCoordinates(orientation);
    }
    
    @Override
    public VacuumWorldCoordinates getForwardRightCoordinates(Orientation orientation) {
        return (VacuumWorldCoordinates) super.getForwardRightCoordinates(orientation);
    }
    
    @Override
    public VacuumWorldCoordinates getLeftCoordinates(Orientation orientation) {
        return (VacuumWorldCoordinates) super.getLeftCoordinates(orientation);
    }
    
    @Override
    public VacuumWorldCoordinates getNeighborCoordinates(Orientation orientation) {
        return new VacuumWorldCoordinates(super.getNeighborCoordinates(orientation));
    }
    
    @Override
    public VacuumWorldCoordinates getNorthEasternCoordinates() {
        return (VacuumWorldCoordinates) super.getNorthEasternCoordinates();
    }
    
    @Override
    public VacuumWorldCoordinates getNorthernCoordinates() {
        return (VacuumWorldCoordinates) super.getNorthernCoordinates();
    }
    
    @Override
    public VacuumWorldCoordinates getNorthWesternCoordinates() {
        return (VacuumWorldCoordinates) super.getNorthWesternCoordinates();
    }
    
    @Override
    public VacuumWorldCoordinates getRightCoordinates(Orientation orientation) {
        return (VacuumWorldCoordinates) super.getRightCoordinates(orientation);
    }
    
    @Override
    public VacuumWorldCoordinates getSouthEasternCoordinates() {
        return (VacuumWorldCoordinates) super.getSouthEasternCoordinates();
    }
    
    @Override
    public VacuumWorldCoordinates getSouthernCoordinates() {
        return (VacuumWorldCoordinates) super.getSouthernCoordinates();
    }
    
    @Override
    public VacuumWorldCoordinates getSouthWesternCoordinates() {
        return (VacuumWorldCoordinates) super.getSouthWesternCoordinates();
    }
    
    @Override
    public VacuumWorldCoordinates getWesternCoordinates() {
        return (VacuumWorldCoordinates) super.getWesternCoordinates();
    }
    
    public boolean match(int x, int y) {
	return getX() == x && getY() == y;
    }
    
    public boolean matchForX(int x) {
	return match(x, getY());
    }
    
    public boolean matchForX(VacuumWorldCoordinates coordinates) {
	return matchForX(coordinates.getX());
    }
    
    public boolean matchForY(int y) {
	return match(getX(), y);
    }
    
    public boolean matchForY(VacuumWorldCoordinates coordinates) {
	return matchForY(coordinates.getY());
    }
    
    public boolean noMatchForXAndY(VacuumWorldCoordinates coordinates) {
	return noMatchForXAndY(coordinates.getX(), coordinates.getY());
    }
    
    private boolean noMatchForXAndY(int x, int y) {
	return x != getX() && y != getY();
    }

    public boolean onTheMainDiagonal() {
	return getX() == getY();
    }
    
    public static VacuumWorldCoordinates randomCoordinates(Integer maxX, Integer maxY) {
	return (VacuumWorldCoordinates) AbstractCoordinates.randomCoordinates(maxX, maxY);
    }
    
    public static VacuumWorldCoordinates of(int x, int y) {
	if(x == 0 && y == 0) {
	    return VacuumWorldCoordinates.origin;
	}
	else {
	    return new VacuumWorldCoordinates(x, y);
	}
    }
}