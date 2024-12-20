package frc.lib.util;

import frc.robot.Constants;

public class Helpers {
    
    public static double objectDistance(double ty, double objectHeightMeters) {

        double angleToTarget = Constants.Vision.yMountDegrees + ty;
        double angleToTargetRadians = Math.toRadians(angleToTarget);

        double distance = (objectHeightMeters - Constants.Vision.lensHeightMeters) / Math.tan(angleToTargetRadians);

        return distance;
    }
}
