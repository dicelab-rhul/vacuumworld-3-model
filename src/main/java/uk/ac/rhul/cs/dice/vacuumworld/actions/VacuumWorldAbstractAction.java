package uk.ac.rhul.cs.dice.vacuumworld.actions;

import java.io.Serializable;
import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldCommunicativeActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldSensingActionsEnum;

public abstract class VacuumWorldAbstractAction implements VacuumWorldAbstractActionInterface, Serializable {
    private static final long serialVersionUID = -600283328134615689L;
    private String actor;
    private static BiMap<Enum<?>, Character> codeMapping = initMapping();
    
    public VacuumWorldAbstractAction() {}
    
    public VacuumWorldAbstractAction(String actor) {
	this.actor = actor;
    }
    
    private static BiMap<Enum<?>, Character> initMapping() {
	BiMap<Enum<?>, Character> map = HashBiMap.create();
	
	Stream.of(VacuumWorldPhysicalActionsEnum.values()).forEach( v -> map.put(v, v.getCode()));
	Stream.of(VacuumWorldSensingActionsEnum.values()).forEach( v -> map.put(v, v.getCode()));
	Stream.of(VacuumWorldCommunicativeActionsEnum.values()).forEach( v -> map.put(v, v.getCode()));
	
	return map;
    }
    
    @Override
    public String getActorID() {
	return this.actor;
    }
    
    @Override
    public void setActor(String actor) {
	this.actor = actor;
    }
    
    public static VacuumWorldAbstractAction generate(char c, Object... additional) {
	return VacuumWorldActionFactory.generate(VacuumWorldAbstractAction.codeMapping.inverse().get(c), additional);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public abstract String toShortString();
}