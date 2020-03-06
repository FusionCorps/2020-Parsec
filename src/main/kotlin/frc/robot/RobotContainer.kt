/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot

import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import frc.robot.commands.autonomous.AutonomousSad
import frc.robot.commands.chassis.ChassisRunJoystick
import frc.robot.commands.hopper.HopperRunAt
import frc.robot.commands.indexer.IndexerRunAtDutyCycle
import frc.robot.commands.lift.LiftExtend
import frc.robot.commands.lift.LiftRetract
import frc.robot.commands.shooter.ShooterCoastDown
import frc.robot.commands.shooter.ShooterRunToVelocity
import frc.robot.subsystems.Cameras
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

    private lateinit var mCameras: Cameras

    private var mAutoCommandChooser: SendableChooser<Command> = SendableChooser()
    val mChassisJoystickDrive = ChassisRunJoystick()

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    init {
        // Configure the button bindings
        configureButtonBindings()
        mAutoCommandChooser.setDefaultOption("Default Auto", mChassisJoystickDrive)
        SmartDashboard.putData("Auto mode", mAutoCommandChooser)

        if (RobotBase.isReal()) {
            mCameras = Cameras
        }
    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    fun configureButtonBindings() {
        JoystickButton(Controls.controller, XboxController.Button.kB.value)
            .whileHeld(HopperRunAt(value = Constants.Hopper.TARGET_VELOCITY))
        JoystickButton(Controls.controller, XboxController.Button.kA.value)
            .whenPressed(ShooterRunToVelocity())
            .whenReleased(ShooterCoastDown())
//        JoystickButton(Controls.controller, XboxController.Button.kA.value)
//            .whenPressed(IndexerDump())
        JoystickButton(Controls.controller, XboxController.Button.kX.value)
            .whileHeld(IndexerRunAtDutyCycle())
//        JoystickButton(Controls.controller, XboxController.Button.kA.value)
//            .whileHeld(IndexerVelocity(DutyCycleConfig(0.4)))
        JoystickButton(Controls.controller, XboxController.Button.kBumperLeft.value)
            .whileHeld(LiftExtend())
        JoystickButton(Controls.controller, XboxController.Button.kBumperRight.value)
            .whileHeld(LiftRetract())
//        JoystickButton(Controls.controller, XboxController.Button.kB.value)
//            .whenPressed(CamerasSwitch())
    }

    fun getAutonomousCommand(): Command {
        // Return the selected command

        return AutonomousSad()

//        var autoVoltageConstraint = DifferentialDriveVoltageConstraint(
//            SimpleMotorFeedforward(
//                Constants.Chassis.VOLTS,
//                Constants.Chassis.VOLT_SEC_PER_METER,
//                Constants.Chassis.VOLT_SEC_SQUARED_PER_METER
//            ),
//            Constants.Chassis.DRIVE_KINEMATICS,
//            10.0
//        )
//        val config = TrajectoryConfig(
//            Constants.Chassis.MAX_SPEED_METERS_PER_SEC,
//            Constants.Chassis.MAX_ACCEL_METERS_PER_SEC_SQUARED
//        )
//            .setKinematics(Constants.Chassis.DRIVE_KINEMATICS)
//            .addConstraint(autoVoltageConstraint)
//        val trajectory = TrajectoryGenerator.generateTrajectory(
//            Pose2d(0.0, 0.0, Rotation2d(0.0)),
//            listOf(
//                Translation2d(1.0, 1.0),
//                Translation2d(2.0, -1.0)
//            ),
//            Pose2d(3.0, 0.0, Rotation2d(0.0)),
//            config
//        )
//
//        return RamseteCommand(
//            trajectory,
//            { Chassis.pose },
//            RamseteController(Constants.Chassis.RAMSETE_B, Constants.Chassis.RAMSETE_ZETA),
//            SimpleMotorFeedforward(Constants.Chassis.VOLTS, Constants.Chassis.VOLT_SEC_PER_METER, Constants.Chassis.VOLT_SEC_SQUARED_PER_METER),
//            Constants.Chassis.DRIVE_KINEMATICS,
//            { Chassis.wheelSpeeds },
//            PIDController(Constants.Chassis.P_DRIVE_VEL, 0.0, 0.0),
//            PIDController(Constants.Chassis.P_DRIVE_VEL, 0.0, 0.0),
//            Chassis::tankDrive,
//            arrayOf(Chassis)
//        ).andThen(Runnable { Chassis.tankDrive(0.0, 0.0) })
    }
}
