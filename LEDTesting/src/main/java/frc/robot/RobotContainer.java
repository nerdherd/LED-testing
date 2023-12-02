// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.CANdleSystem;
import frc.robot.subsystems.CANdleSystem.AnimationTypes;
import frc.robot.subsystems.CANdleSystem.Status;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandPS4Controller m_driverController =
      new CommandPS4Controller(OperatorConstants.kDriverControllerPort);

  private CANdleSystem led;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    led = new CANdleSystem();

    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    m_driverController.square().onTrue(Commands.runOnce(() -> led.setColorBasedOnStatus(Status.AUTO)));
    m_driverController.cross().onTrue(Commands.runOnce(() -> led.setColorBasedOnStatus(Status.DISABLED)));
    m_driverController.circle().onTrue(Commands.runOnce(() -> led.setColorBasedOnStatus(Status.DISCONNECTED)));
    m_driverController.triangle().onTrue(Commands.runOnce(() -> led.setColorBasedOnStatus(Status.TELEOP)));
    m_driverController.L2().onTrue(Commands.runOnce(() -> led.changeAnimation(AnimationTypes.ColorFlow)));
    m_driverController.L1().onTrue(Commands.runOnce(() -> led.changeAnimation(AnimationTypes.Twinkle)));
    m_driverController.R1().onTrue(Commands.runOnce(() -> led.changeAnimation(AnimationTypes.ColorFlow)));
    m_driverController.R2().onTrue(Commands.runOnce(() -> led.changeAnimation(AnimationTypes.Larson)));
    m_driverController.share().onTrue(Commands.runOnce(() -> led.changeAnimation(AnimationTypes.Rainbow)));
    m_driverController.options().onTrue(Commands.runOnce(() -> led.changeAnimation(AnimationTypes.TwinkleOff)));
    m_driverController.L3().onTrue(Commands.runOnce(() -> led.changeAnimation(AnimationTypes.Strobe)));
    m_driverController.R3().onTrue(Commands.runOnce(() -> led.changeAnimation(AnimationTypes.SingleFade)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
