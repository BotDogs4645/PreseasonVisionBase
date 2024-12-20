package frc.robot;

import com.pathplanner.lib.util.PIDConstants;

public class Constants {
    public static class DrivetrainConstants {
        /* Specifies the Driver Station port index for the driver's controller. */
        public static final int driveControllerPort = 0;
        /* Joystick deadband that will be applied to the drivetrain inputs. */
        public static final double stickDeadband = 0.1;

        /* Maximum drivetrain speed in meters per second. */
        public static final double maxSpeed = 4.5;
        /* Number of seconds to wait after the robot is disabled before setting drive motors to neutral. */
        /* Allows the robot to stop instantly, but is easier to push after the delay. */
        public static final int disableBreakTime = 10;

        /* PID Constants passed to PathPlanner. */
        public static final PIDConstants autonTranslationPID = new PIDConstants(1, 0, 0);
        public static final PIDConstants autonAnglePID = new PIDConstants(1, 0, 0);

    }
    public class Vision{
        public static final String frontLimelightName = "limelight-front";

        // Mount specs (Limelight)
        public static final double yMountDegrees = 35;
        public static final double lensHeightMeters = 1;
        
        public static final double noteHeightMeters = 0;
    }
}
