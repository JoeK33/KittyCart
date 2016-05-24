package com.myreliablegames.kittycart.Tracks;

import com.badlogic.gdx.math.Vector2;
import com.myreliablegames.kittycart.Zone;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/18/2016.
 * Builds bits of track to play in various ways.  Sections must begin and end with an actual track piece, no gaps or shifts. Easiest
 * way is to cap them with a single straight piece.
 */
public class TrackSectionFactory {

    private TrackBuilder builder;
    private int sectionLength;

    public TrackSectionFactory() {
        sectionLength = (int) (Constants.WORLD_WIDTH / Constants.TRACK_WIDTH) * 2;
    }

    public TrackSection makeStraightSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(sectionLength);
        return builder.build();

    }

    public TrackSection makeCorrespondingSection(Zone zone, Vector2 position) {
        switch (zone) {
            case DESERT:
                return makeRandomSmallGapShiftSection(position);
            case FOREST:
                return makeRandomSection(position);
            case MOUNTAIN:
                return makeRandomGapShiftSection(position);
            case OCEAN:
                return makeRandomGapSection(position);
            case PLAINS:
                return makeRandomJaggedNoGapShiftSection(position);
            default:
                return makeStraightSection(position);
        }
    }

    public TrackSection makeRandomSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder = addRandomConnectedSection(builder, 5);
        builder = addRandomShiftSection(builder, 3);
        builder = addRandomConnectedSection(builder, 5);
        builder = addRandomGapSection(builder, 3);
        builder.addStraight(1);

        return builder.build();
    }

    public TrackSection makeRandomGapSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder = addRandomConnectedSection(builder, 3);
        builder.addStraight(2);
        builder = addRandomGapSection(builder, 4);
        builder = addRandomConnectedSection(builder, 3);
        builder.addStraight(2);
        builder = addRandomGapSection(builder, 4);
        builder.addStraight(1);

        return builder.build();
    }

    public TrackSection makeRandomSmallGapShiftSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder = addRandomConnectedSection(builder, 3);
        builder = addRandomShiftSection(builder, 4);
        builder = addRandomGapSection(builder, 2);
        builder = addRandomConnectedSection(builder, 3);
        builder = addRandomShiftSection(builder, 4);
        builder.addStraight(1);
        return builder.build();
    }

    public TrackSection makeRandomGapShiftSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder = addRandomConnectedSection(builder, 2);
        builder.addStraight(1);
        builder = addRandomShiftSection(builder, 3);
        builder = addRandomGapSection(builder, 3);
        builder = addRandomConnectedSection(builder, 2);
        builder.addStraight(1);
        builder = addRandomShiftSection(builder, 3);
        builder = addRandomGapSection(builder, 3);
        builder.addStraight(1);
        return builder.build();
    }

    public TrackSection makeRandomJaggedNoGapShiftSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder = addRandomConnectedSection(builder, 2);
        builder = addRandomShiftSection(builder, 4);
        builder = addRandomConnectedSection(builder, 2);
        builder = addRandomShiftSection(builder, 4);
        builder.addStraight(1);
        return builder.build();
    }


    private TrackBuilder addRandomConnectedSection(TrackBuilder builder, int length) {
        int connectedOptions = 3;

        for (int i = 0; i < length; i++) {

            // Randomly choose a builder option
            int choice = (int) ((Math.random() * connectedOptions) + 1);

            // generate random lengths of track
            int howMany = (int) ((Math.random() * 3) + 1);

            switch (choice) {
                case 0:
                    builder.addStraight(howMany);
                    break;
                case 1:
                    builder.addUp(howMany);
                    break;
                case 2:
                    builder.addDown(howMany);
                    break;
                default:
                    builder.addStraight(howMany);
                    break;
            }
        }
        return builder;

    }

    // Randomly shifts track up to 3 shifts in either direction
    private TrackBuilder addRandomShiftSection(TrackBuilder builder, int maxShifts) {
        int shiftOptions = 2;

        // Randomly choose a shift direction
        int choice = (int) ((Math.random() * shiftOptions) + 1);

        // generate random lengths of track
        int howMany = (int) ((Math.random() * maxShifts) + 1);

        switch (choice) {
            case 0:
                builder.shiftUp(howMany);
                break;
            case 1:
                builder.shiftDown(howMany);
                break;
            default:
                builder.shiftUp(howMany);
                break;
        }
        return builder;
    }

    // Randomly gaps track up to 4 units, minimum of 2 units. Cart skips over gaps of 1.
    private TrackBuilder addRandomGapSection(TrackBuilder builder, int maxGaps) {
        int minimumGap = 2;
        // generate random length of gap
        int howMany = (int) ((Math.random() * maxGaps) + minimumGap);
        builder.addGap(howMany);

        return builder;

    }
}
