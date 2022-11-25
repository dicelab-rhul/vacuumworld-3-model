package uk.ac.rhul.cs.dice.vacuumworld.actions;

import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldCommunicativeActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldSensingActionsEnum;

public abstract class VacuumWorldAbstractAction implements VacuumWorldAbstractActionInterface {
    private static final long serialVersionUID = -600283328134615689L;
    private String actor;
    private String shortStringRepresentation;
    private static BiMap<Enum<?>, String> codeMapping = initMapping();

    protected VacuumWorldAbstractAction() {
        this("-UNDEFINED-ACTOR-", "-UNDEFINED-");
    }

    protected VacuumWorldAbstractAction(String shortStringRepresentation) {
        this("-UNDEFINED-", shortStringRepresentation);
    }

    protected VacuumWorldAbstractAction(String actor, String shortStringRepresentation) {
        this.actor = actor;
        this.shortStringRepresentation = shortStringRepresentation;
    }

    private static BiMap<Enum<?>, String> initMapping() {
        BiMap<Enum<?>, String> map = HashBiMap.create();

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

    public static VacuumWorldAbstractAction generate(String code, Object... additional) {
        return VacuumWorldActionFactory.generate(VacuumWorldAbstractAction.codeMapping.inverse().get(code), additional);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toShortString() {
        return this.shortStringRepresentation;
    }
}
