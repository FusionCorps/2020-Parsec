/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.robot.commands.chassis.ChassisJoystickDrive
import frc.robot.commands.hopper.HopperSetMovementCharacteristics
import frc.robot.commands.indexer.IndexerDump
import frc.robot.commands.lift.LiftExtend
import frc.robot.commands.shooter.ShooterCoastDown
import frc.robot.commands.shooter.ShooterRunToVelocity
import frc.robot.subsystems.Chassis
import frc.robot.subsystems.Hopper
import frc.robot.subsystems.Indexer
import frc.robot.subsystems.Intake
import frc.robot.subsystems.Lift
import frc.robot.subsystems.Shooter

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private val mChassis = Chassis
    private val mHopper = Hopper
    private val mIntake = Intake
    private val mIndexer = Indexer
    private val mShooter = Shooter
    private val mLift = Lift

    private var mAutoCommandChooser: SendableChooser<Command> = SendableChooser()
    val mChassisJoystickDrive = ChassisJoystickDrive(mChassis)

    val iNetworkTables = NetworkTableInstance.getDefault()

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    init {
        // Configure the button bindings
        configureButtonBindings()
        mAutoCommandChooser.setDefaultOption("Default Auto", mChassisJoystickDrive)
        SmartDashboard.putData("Auto mode", mAutoCommandChooser)
    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    fun configureButtonBindings() {
        JoystickButton(Controls.controller, XboxController.Button.kX.value)
            .whenPressed(HopperSetMovementCharacteristics(mHopper, Constants.Hopper.TARGET_VELOCITY))
            .whenReleased(HopperSetMovementCharacteristics(mHopper, 0.0))

        JoystickButton(Controls.controller, XboxController.Button.kY.value)
            .whenPressed(ShooterRunToVelocity(mShooter))
            .whenReleased(ShooterCoastDown(mShooter))

        JoystickButton(Controls.controller, XboxController.Button.kA.value)
            .whenPressed(IndexerDump(mIndexer))

        JoystickButton(Controls.controller, XboxController.Button.kBumperLeft.value)
                .whenPressed(LiftExtend(mLift))
    }

    fun getAutonomousCommand(): Command {
        // Return the selected command
        return mAutoCommandChooser.selected
    }
}
