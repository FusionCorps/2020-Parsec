package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.geometry.Pose2d
import edu.wpi.first.wpilibj.geometry.Rotation2d
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.chassis.ChassisRunJoystick
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.fusion.motion.FTalonFX
import frc.robot.fusion.motion.FollowerConfig
import frc.robot.fusion.motion.MotionCharacteristics
import frc.robot.fusion.motion.MotorID
import frc.robot.fusion.motion.MotorModel
import kotlin.math.IEEErem
import kotlin.math.PI

object Chassis : SubsystemBase() { // Start by defining motors
    // Motor Controllers
    private val talonFXFrontLeft = FTalonFX(MotorID(Constants.Chassis.ID_TALONFX_F_L, "talonFXFrontLeft", MotorModel.TalonFX)).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)
        setInverted(TalonFXInvertType.Clockwise)
        configNeutralDeadband(0.05)
        selectedSensorPosition = 0
    }
    private val talonFXBackLeft = FTalonFX(MotorID(Constants.Chassis.ID_TALONFX_B_L, "talonFXBackLeft", MotorModel.TalonFX)).apply {
        setInverted(TalonFXInvertType.FollowMaster)
        control(ControlMode.Follower, FollowerConfig(talonFXFrontLeft))
        configNeutralDeadband(0.05)
    }
    private val talonFXFrontRight = FTalonFX(MotorID(Constants.Chassis.ID_TALONFX_F_R, "talonFXFrontRight", MotorModel.TalonFX)).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)
        setInverted(TalonFXInvertType.CounterClockwise)
        configNeutralDeadband(0.05)
        selectedSensorPosition = 0
    }
    private val talonFXBackRight = FTalonFX(MotorID(Constants.Chassis.ID_TALONFX_B_R, "talonFXBackRight", MotorModel.TalonFX)).apply {
        setInverted(TalonFXInvertType.FollowMaster)
        control(ControlMode.Follower, FollowerConfig(talonFXFrontRight))
        configNeutralDeadband(0.05)
    }
    private val axisControllerFrontLeft = FTalonFX(MotorID(Constants.Chassis.ID_AXIS_F_L, "axisFrontLeft", MotorModel.TalonFX)).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)
        setInverted(TalonFXInvertType.Clockwise)
        configNeutralDeadband(0.05)
        selectedSensorPosition = 0
    }
    private val axisControllerBackLeft = FTalonFX(MotorID(Constants.Chassis.ID_AXIS_B_L, "axisBackLeft", MotorModel.TalonFX)).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)
        setInverted(TalonFXInvertType.Clockwise)
        configNeutralDeadband(0.05)
        selectedSensorPosition = 0
    }
    private val axisControllerFrontRight = FTalonFX(MotorID(Constants.Chassis.ID_AXIS_F_R, "axisFrontRight", MotorModel.TalonFX)).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)
        setInverted(TalonFXInvertType.Clockwise)
        configNeutralDeadband(0.05)
        selectedSensorPosition = 0
    }
    private val axisControllerBackRight = FTalonFX(MotorID(Constants.Chassis.ID_AXIS_B_R, "axisBackRight", MotorModel.TalonFX)).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)
        setInverted(TalonFXInvertType.Clockwise)
        configNeutralDeadband(0.05)
        selectedSensorPosition = 0
    }


    // wheel position sensor sets
    val leftPosition: Double get() = talonFXFrontLeft.selectedSensorPosition / 4096 * 2 * PI * Constants.Chassis.WHEEL_RADIUS_METERS
    val rightPosition: Double get() = talonFXFrontRight.selectedSensorPosition / 4096 * 2 * PI * Constants.Chassis.WHEEL_RADIUS_METERS
    val wheelSpeeds: DifferentialDriveWheelSpeeds
        get() = DifferentialDriveWheelSpeeds(
            talonFXFrontLeft.selectedSensorVelocity.toDouble() / 4096 * 2 * PI * Constants.Chassis.WHEEL_RADIUS_METERS * 10,
            talonFXFrontRight.selectedSensorVelocity.toDouble() / 4096 * 2 * PI * Constants.Chassis.WHEEL_RADIUS_METERS * 10
        )

    private val drive = DifferentialDrive(talonFXFrontLeft, talonFXFrontRight) // set drive

    private val ahrs = AHRS(SPI.Port.kMXP).apply {
        calibrate() // motion sensor
    }

    var generalMotionCharacteristics = MotionCharacteristics(ControlMode.DutyCycle, dutyCycleConfig = DutyCycleConfig(0.5))

    val odometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(heading))

    fun resetOdometry(pose: Pose2d) {
        odometry.resetPosition(pose, Rotation2d.fromDegrees(heading))
    }

    val heading: Double get() = ahrs.angle.IEEErem(360.0)

    fun resetHeading() {
        ahrs.reset()
    }

    val turnRate: Double get() = ahrs.rate

    val pose: Pose2d get() = odometry.poseMeters

    init {
        defaultCommand = ChassisRunJoystick() // Set default state to run with joystick

        Shuffleboard.getTab("Chassis").add(talonFXFrontRight)
        Shuffleboard.getTab("Chassis").add(talonFXFrontLeft)
        Shuffleboard.getTab("Chassis").add(talonFXBackRight)
        Shuffleboard.getTab("Chassis").add(talonFXBackLeft)

        Shuffleboard.getTab("Chassis").add(this)
    }

    override fun periodic() {
        odometry.update(Rotation2d(heading), leftPosition, rightPosition) // Update sensor data
    }

    fun joystickDrive(x: Double, z: Double) {
        drive.curvatureDrive( // Run with joystick
            x * generalMotionCharacteristics.dutyCycleConfig!!.dutyCycle,
            z * generalMotionCharacteristics.dutyCycleConfig!!.dutyCycle, true
        )
    }

    fun tankDrive(left: Double, right: Double) { // Run as tank
        drive.tankDrive(left, right)
    }

    /** SWERVE DRIVE EXPLANATION:
     *
     *              ^
     *              \ FORWARD
     *  ____________\______________
     *  |                          |
     *  |                          |
     *  |                          |
     *  |             ___          |
     *  |           /    \         |  STRAFING
     *  |          ^  X  |         | --->
     *  |          \ ___/          |
     *  |         ROTATION         |
     *  |                          |
     *  |                          |
     *  |__________________________|
     *
     *  The X AXIS of the LEFT joystick controls strafing
     *  The Y AXIS of the LEFT joystick controls forward motion
     *  The X AXIS of the RIGHT joystick controls rotation
     *
     *  See https://docs.google.com/document/d/1iRMtpHU5ED9dGsRdzCR48bUF6w-XXB84SJX8Oc-fEmo/edit?usp=sharing
     *  for derivation
     *
     *  Command: forward, strafe are wheel speeds (percentage for now)
     *           rotation is clockwise
     *
     *  Axis motors are configured to turn counterclockwise (we install them upside down)
     */

    fun swerveDrive(foward: Double, strafe: Double, rotation: Double) {
        
    }

}
