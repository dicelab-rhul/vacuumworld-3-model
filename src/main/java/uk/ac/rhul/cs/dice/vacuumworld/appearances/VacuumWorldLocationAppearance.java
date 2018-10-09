package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.agent.interfaces.ActiveBody;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldSerializer;
import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;
import uk.ac.rhul.cs.dice.vacuumworld.exceptions.VacuumWorldRuntimeException;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldLocationPerceptionInterface;

import com.google.gson.JsonObject;

/**
 * 
 * Implementation of {@link Appearance}, {@link VacuumWorldLocationPerceptionInterface}, and {@link Comparable} (with
 * {@link VacuumWorldLocationAppearance}).
 * 
 * @author cloudstrife9999
 *
 */
public class VacuumWorldLocationAppearance implements Appearance, VacuumWorldLocationPerceptionInterface, Comparable<VacuumWorldLocationAppearance> {
	private static final long serialVersionUID = -2552147509566518640L;
	private static final int MAX_WALLS = 4;
	private static final String TEXTUAL_BORDER = "X\n";
	private VacuumWorldActorAppearance activeBodyAppearance;
	private VacuumWorldDirtAppearance dirtAppearance;
	private VacuumWorldCoordinates coordinates;
	private boolean wallOnNorth;
	private boolean wallOnSouth;
	private boolean wallOnWest;
	private boolean wallOnEast;

	/**
	 * 
	 * Constructs a {@link VacuumWorldLocationAppearance} from the {@link VacuumWorldCoordinates}, the
	 * {@link Appearance} of an {@link ActiveBody}, a {@link VacuumWorldDirtAppearance} and the {@link Boolean} flags
	 * for the walls.
	 * 
	 * @param coordinates
	 *            the {@link VacuumWorldCoordinates}.
	 * @param activeBodyAppearance
	 *            the {@link Appearance} of an {@link ActiveBody}.
	 * @param dirtAppearance
	 *            a {@link VacuumWorldDirtAppearance}.
	 * @param walls
	 *            the {@link Boolean} flags for the walls (NORTH, SOUTH, WEST, and EAST).
	 * 
	 */
	public VacuumWorldLocationAppearance(VacuumWorldCoordinates coordinates, VacuumWorldActorAppearance activeBodyAppearance,
			VacuumWorldDirtAppearance dirtAppearance, boolean... walls) {
		this.activeBodyAppearance = activeBodyAppearance;
		this.dirtAppearance = dirtAppearance;
		this.coordinates = coordinates;

		initWalls(walls);
	}

	private void initWalls(boolean[] walls) {
		if (walls.length != MAX_WALLS) {
			throw new IllegalArgumentException("Expected 4 pieces of information about walls (N, S, W, E), got " + walls.length + ".");
		}

		this.wallOnNorth = walls[0];
		this.wallOnSouth = walls[1];
		this.wallOnWest = walls[2];
		this.wallOnEast = walls[3];
	}

	@Override
	public VacuumWorldActorAppearance getActiveBodyAppearanceIfAny() {
		return this.activeBodyAppearance;
	}

	@Override
	public VacuumWorldDirtAppearance getDirtAppearanceIfAny() {
		return this.dirtAppearance;
	}

	@Override
	public VacuumWorldCoordinates getCoordinates() {
		return this.coordinates;
	}

	@Override
	public boolean isWallOnNorth() {
		return this.wallOnNorth;
	}

	@Override
	public boolean isWallOnSouth() {
		return this.wallOnSouth;
	}

	@Override
	public boolean isWallOnWest() {
		return this.wallOnWest;
	}

	@Override
	public boolean isWallOnEast() {
		return this.wallOnEast;
	}

	@Override
	public boolean isWallInFront(String actorId) {
		checkForActiveBody(actorId, "\"In front of ");

		return checkForWall(getActiveBodyOrientation());
	}

	@Override
	public boolean isWallOnTheLeft(String actorId) {
		checkForActiveBody(actorId, "\"On the left of ");

		return checkForWall(getActiveBodyOrientation().getLeft());
	}

	@Override
	public boolean isWallOnTheRight(String actorId) {
		checkForActiveBody(actorId, "\"On the right of ");

		return checkForWall(getActiveBodyOrientation().getRight());
	}

	@Override
	public boolean isWallBehind(String actorId) {
		checkForActiveBody(actorId, "\"Behind ");

		return checkForWall(getActiveBodyOrientation().getOpposite());
	}

	private void checkForActiveBody(String actorId, String message) {
		if (!containsSuchActiveBody(actorId)) {
			throw new VacuumWorldRuntimeException(message + actorId
					+ "\" does not make sense, because there is no such active body on this location.");
		}
	}

	@Override
	public boolean checkForWall(Orientation orientation) {
		switch (orientation) {
		case NORTH:
			return isWallOnNorth();
		case SOUTH:
			return isWallOnSouth();
		case WEST:
			return isWallOnWest();
		case EAST:
			return isWallOnEast();
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean isACleaningAgentThere() {
		return isAnActiveBodyThere() && this.activeBodyAppearance.isCleaningAgent();
	}

	@Override
	public boolean isAGreenAgentThere() {
		return isAnActiveBodyThere() && this.activeBodyAppearance.isGreenAgent();
	}

	@Override
	public boolean isAnOrangeAgentThere() {
		return isAnActiveBodyThere() && this.activeBodyAppearance.isOrangeAgent();
	}

	@Override
	public boolean isAWhiteAgentThere() {
		return isAnActiveBodyThere() && this.activeBodyAppearance.isWhiteAgent();
	}

	@Override
	public boolean isACleaningAgentWithSuchColorThere(AgentColor color) {
		return isACleaningAgentThere() && color.equals(this.activeBodyAppearance.getColor());
	}

	@Override
	public boolean isACleaningAgentCompatibleWithSuchDirtThere(VacuumWorldDirtColor color) {
		return isACleaningAgentThere() && color.canBeCleanedBy(this.activeBodyAppearance.getColor());
	}

	@Override
	public boolean isAUserThere() {
		return isAnActiveBodyThere() && this.activeBodyAppearance.isUser();
	}

	@Override
	public boolean isAnAvatarThere() {
		return isAnActiveBodyThere() && this.activeBodyAppearance.isAvatar();
	}

	@Override
	public boolean isAnActiveBodyThere() {
		return this.activeBodyAppearance != null;
	}

	@Override
	public boolean isFreeFromActiveBodies() {
		return this.activeBodyAppearance == null;
	}

	@Override
	public boolean isDirtThere() {
		return this.dirtAppearance != null;
	}

	@Override
	public boolean isFreeFromDirt() {
		return this.dirtAppearance == null;
	}

	@Override
	public boolean isSuchDirtThere(VacuumWorldDirtColor color) {
		return isDirtThere() && color != null && color.equals(this.dirtAppearance.getColor());
	}

	@Override
	public boolean isCompatibleDirtThere(AgentColor color) {
		return isDirtThere() && color != null && color.canClean(this.dirtAppearance.getColor());
	}

	@Override
	public boolean isGreenDirtThere() {
		return isDirtThere() && VacuumWorldDirtColor.GREEN.equals(this.dirtAppearance.getColor());
	}

	@Override
	public boolean isOrangeDirtThere() {
		return isDirtThere() && VacuumWorldDirtColor.ORANGE.equals(this.dirtAppearance.getColor());
	}

	@Override
	public VacuumWorldAutonomousActorAppearance getAgentAppearanceIfAny() {
		return isACleaningAgentThere() ? (VacuumWorldAutonomousActorAppearance) this.activeBodyAppearance : null;
	}

	@Override
	public VacuumWorldAutonomousActorAppearance getUserAppearanceIfAny() {
		return isAUserThere() ? (VacuumWorldAutonomousActorAppearance) this.activeBodyAppearance : null;
	}

	@Override
	public VacuumWorldAvatarAppearance getAvatarAppearanceIfAny() {
		return isAnAvatarThere() ? (VacuumWorldAvatarAppearance) this.activeBodyAppearance : null;
	}

	/**
	 * 
	 * Returns whether this {@link VacuumWorldLocation} <code>x</code> coordinate is smaller, equal, or bigger that
	 * <code>other</code>'s <code>x</code> coordinate.
	 * 
	 * @return whether this {@link VacuumWorldLocation} <code>x</code> coordinate is smaller, equal, or bigger that
	 *         <code>other</code>'s <code>x</code> coordinate.
	 * 
	 */
	@Override
	public int compareTo(VacuumWorldLocationAppearance other) {
		return Integer.valueOf(getCoordinates().getX()).compareTo(other.getCoordinates().getX());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((this.coordinates == null) ? 0 : this.coordinates.hashCode());
		result = prime * result + (this.wallOnEast ? 1231 : 1237);
		result = prime * result + (this.wallOnNorth ? 1231 : 1237);
		result = prime * result + (this.wallOnSouth ? 1231 : 1237);
		result = prime * result + (this.wallOnWest ? 1231 : 1237);

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof VacuumWorldLocationAppearance && checkFields((VacuumWorldLocationAppearance) obj);
	}

	private boolean checkFields(VacuumWorldLocationAppearance obj) {
		return obj != null && this.coordinates.equals(obj.coordinates) && checkWalls(obj);
	}

	private boolean checkWalls(VacuumWorldLocationAppearance obj) {
		return this.wallOnNorth == obj.wallOnNorth && this.wallOnSouth == obj.wallOnSouth && this.wallOnWest == obj.wallOnWest
				&& this.wallOnEast == obj.wallOnEast;
	}

	/**
	 * 
	 * Serializes this {@link VacuumWorldLocationAppearance} to a {@link JsonObject}.
	 * 
	 * @return the {@link JsonObject} serialization of this {@link VacuumWorldLocationAppearance}.
	 * 
	 */
	@Override
	public JsonObject serialize() {
		return VacuumWorldSerializer.serialize(this);
	}

	/**
	 * 
	 * Returns a textual representation of this {@link VacuumWorldLocation}.
	 * 
	 * @return a textual representation of this {@link VacuumWorldLocation}.
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("#########\n");
		builder.append("#       #\n");
		builder.append(getMiddleLine());
		builder.append("#       #\n");
		builder.append("#########\n");

		return builder.toString();
	}

	public String toSimpleString() {
		if (isAnActiveBodyThere()) {
			return "agent-" + this.activeBodyAppearance.getId() + ": " + this.activeBodyAppearance.getColor().toString();
		} else if (isDirtThere()) {
			return "dirt: " + this.dirtAppearance.getColor().toString();
		} else {
			return "empty";
		}
	}

	private Object getMiddleLine() {
		StringBuilder builder = new StringBuilder();

		builder.append("#");

		if (isACleaningAgentThere()) {
			builder.append(getMiddleLineWithAgent());
		} else if (isAUserThere()) {
			builder.append(getMiddleLineWithUser());
		} else if (isAnAvatarThere()) {
			builder.append(getMiddleLineWithAvatar());
		} else if (isDirtThere()) {
			builder.append(getMiddleLineWithDirt());
		} else {
			builder.append("       ");
			builder.append(TEXTUAL_BORDER);
		}

		return builder.toString();
	}

	private String getMiddleLineWithDirt() {
		return "   " + this.dirtAppearance.getColor().toChar() + "   " + TEXTUAL_BORDER;
	}

	private String getMiddleLineWithAvatar() {
		if (isDirtThere()) {
			return "  " + ActorType.AVATAR.toChar() + "+" + this.dirtAppearance.getColor().toChar() + "  " + TEXTUAL_BORDER;
		} else {
			return "   " + ActorType.AVATAR.toChar() + "   " + TEXTUAL_BORDER;
		}
	}

	private String getMiddleLineWithUser() {
		if (isDirtThere()) {
			return "  " + ActorType.USER.toChar() + "+" + this.dirtAppearance.getColor().toChar() + "  " + TEXTUAL_BORDER;
		} else {
			return "   " + ActorType.USER.toChar() + "   " + TEXTUAL_BORDER;
		}
	}

	private String getMiddleLineWithAgent() {
		if (isDirtThere()) {
			return "  " + this.activeBodyAppearance.getColor().toChar() + "+" + this.dirtAppearance.getColor().toChar() + "  " + TEXTUAL_BORDER;
		} else {
			return "   " + this.activeBodyAppearance.getColor().toChar() + "   " + TEXTUAL_BORDER;
		}
	}
}