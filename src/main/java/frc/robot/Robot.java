// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.util.LimelightHelpers;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private Command m_teleopCommand;

  private RobotContainer m_robotContainer;

  private Timer disabledTimer;
  
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    disabledTimer = new Timer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber("limelight-front-pose", LimelightHelpers.getBotPose3d_TargetSpace(Constants.Vision.frontLimelightName).getX());

  }

  @Override
  public void disabledInit() {
    // Unschedule commands
    if (m_autonomousCommand != null)
      m_autonomousCommand.cancel();
    if (m_teleopCommand != null)
      m_teleopCommand.cancel();


    // Set velocity to zero when the robot is disabled.
    m_robotContainer.stop();

    /* Enable brakes when robot is disabled to stop immediatly 
     * Then, set a timer so breaks can be disabled and the robot can be pushed.
    */
    m_robotContainer.setMotorBrake(true);
    disabledTimer.reset();
    disabledTimer.start();
  }

  @Override
  public void disabledPeriodic() {
    /* After a set time, disable the robot brakes to make it easier to push. */
    if (disabledTimer.hasElapsed(Constants.DrivetrainConstants.disableBreakTime))
    {
      m_robotContainer.setMotorBrake(false);
      disabledTimer.stop();
      disabledTimer.reset();
    }
  }

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      /* Enable motor braking when the robot is enabled so the robot does not coast. */
      m_robotContainer.setMotorBrake(true);
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    m_teleopCommand = m_robotContainer.getTeleopCommand();
    if (m_teleopCommand != null) {
      /* Enable motor braking when the robot is enabled so the robot does not coast. */
      m_robotContainer.setMotorBrake(true);

      m_teleopCommand.schedule();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
