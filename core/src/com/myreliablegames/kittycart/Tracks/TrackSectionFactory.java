package com.myreliablegames.kittycart.Tracks;

import com.badlogic.gdx.math.Vector2;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/18/2016.
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

    public TrackSection makeEasySection(Vector2 position) {

        int choice = (int) (Math.random() * 7);
        TrackSection section;

        switch (choice) {
            case 0:
                section = makeJump(position);
                break;
            case 1:
                section = makePlateau(position);
                break;
            case 2:
                section = makeValley(position);
                break;
            case 3:
                section = makeStepsSection(position);
                break;
            case 4:
                section = makeGapsSection(position);
                break;
            case 5:
                section = makeMountainSection(position);
                break;
            case 6:
                section = makeSmallStepsSection(position);
                break;
            default:
                section = makeStraightSection(position);
                break;

        }

        return section;

    }

    public TrackSection makeJump(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(3).addUp(1).addGap(3).addDown(1).addStraight(3);
        return builder.build();
    }

    public TrackSection makePlateau(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(3).addUp(1).addStraight(3).addDown(1).addStraight(3);
        return builder.build();
    }

    public TrackSection makeValley(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(3).addUp(2).addDown(3).addStraight(1).addUp(3).addDown(2).addStraight(3);
        return builder.build();
    }

    public TrackSection makeStepsSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(3).shiftUp(2).addStraight(3).shiftUp(2).addStraight(3).shiftDown(4);
        return builder.build();
    }

    public TrackSection makeGapsSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(3).addGap(2).addStraight(3).addGap(2).addStraight(3);
        return builder.build();
    }

    public TrackSection makeMountainSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(3).addUp(5).addDown(5).addStraight(3);
        return builder.build();
    }

    public TrackSection makeSmallStepsSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(2).shiftUp(2).addStraight(2).shiftUp(2).addStraight(2).shiftUp(2).shiftDown(6).addStraight(5);
        return builder.build();
    }


    public TrackSection makeRandomStepSection(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);

        builder = addRandomConnectedSection(builder, 5);
        builder = addRandomShiftSection(builder);
        builder = addRandomConnectedSection(builder, 5);
        builder = addRandomGapSection(builder);
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
    private TrackBuilder addRandomShiftSection(TrackBuilder builder) {
        int shiftOptions = 2;

        // Randomly choose a shift direction
        int choice = (int) ((Math.random() * shiftOptions) + 1);

        // generate random lengths of track
        int howMany = (int) ((Math.random() * 3) + 1);

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

    // Randomly gaps track up to 4 units, minimum of 2 units
    private TrackBuilder addRandomGapSection(TrackBuilder builder) {
        int minimumGap = 2;
        // generate random length of gap
        int howMany = (int) ((Math.random() * 3) + minimumGap);
        builder.addGap(howMany);

        return builder;

    }
}
