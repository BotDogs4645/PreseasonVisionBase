package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.lib.util.LimelightHelpers;
import frc.robot.subsystems.SwerveSubsystem;

public class CommandBuilder {
   // Test commit
    public class Vision {

        public static Command aimAtTarget(String limelightName, SwerveSubsystem swerveSubsystem) {
            return Commands.run(() -> {
                double[] targetPose = LimelightHelpers.getTargetPose_RobotSpace(limelightName);
                if (targetPose.length > 1 && (targetPose[0] != 0 || targetPose[1] != 0)) {
                    //Get the tag's offset from the robot
                    Rotation2d tagRotation = new Rotation2d(targetPose[0], targetPose[1]).plus(Rotation2d.fromDegrees(90));
                    swerveSubsystem.drive(new Translation2d(Math.hypot(targetPose[0], targetPose[1]) * 0.4,0), tagRotation.getRadians() * -2, false);
                } else {
                    swerveSubsystem.drive(new Translation2d(0,0), 0, false);
                }
            });
        }

        public static Command aimAtTargetRevised(String limelightName, SwerveSubsystem swerveSubsystem) {
            return Commands.runOnce(() -> {
                Pose2d tagPose = LimelightHelpers.getTargetPose3d_RobotSpace(limelightName).toPose2d();
                System.out.println("RUN");
                if (Math.hypot(tagPose.getX(), tagPose.getY()) > 1) {
                    Pose2d invertedTagPose = tagPose.plus(new Transform2d(new Translation2d(), new Rotation2d(180)));

                    Transform2d poseRelative = invertedTagPose.minus(new Pose2d());
                    Pose2d targetPose = swerveSubsystem.getPose().plus(poseRelative);
                
                    swerveSubsystem.driveToPose(targetPose).schedule();
                    System.out.println("SCHEDULED");
                } else {
                    // swerveSubsystem.drive(new Translation2d(0, 0), 0, false);
                }
            });
        }

        public static Command aimAtNote(String limelightName, SwerveSubsystem swerveSubsystem) {
            return Commands.run(() -> {
                double tx = LimelightHelpers.getTX(limelightName);
                int ta = (int)LimelightHelpers.getTA(limelightName);
                double speedLimiter = (100 - (ta * 2.0)) / 100.0;
                if(Math.abs(tx) > 5) {
                    swerveSubsystem.drive(new Translation2d(0.3 * speedLimiter,0), Math.toRadians(tx) * -2, false);

                }
                else if(Math.abs(tx) > 0) {
                    swerveSubsystem.drive(new Translation2d(0.3 * speedLimiter,0), 0, false);
                }
                else {
                    swerveSubsystem.drive(new Translation2d(0,0), 0, false);
                }
            });
        } 
    }

}
