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

        int choice = (int) (Math.random() * 5);
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
            default:
                section = makeStraightSection(position);
                break;

        }

        return section;

    }

    public TrackSection makeJump(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(3).addUp(2).addGap(3).addDown(2).addStraight(3);
        return builder.build();
    }

    public TrackSection makePlateau(Vector2 position) {
        builder = new TrackBuilder(position, Constants.TRACK_SPEED);
        builder.addStraight(3).addUp(2).addStraight(3).addDown(2).addStraight(3);
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
}
