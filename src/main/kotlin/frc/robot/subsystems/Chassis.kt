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

object Chassis : SubsystemBase() {
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

    val leftPosition: Double
        get() {
            return talonFXFrontLeft.selectedSensorPosition / 4096 * 2 * PI * Constants.Chassis.WHEEL_RADIUS_METERS
        }
    val rightPosition: Double
        get() {
            return talonFXFrontRight.selectedSensorPosition / 4096 * 2 * PI * Constants.Chassis.WHEEL_RADIUS_METERS
        }
    val wheelSpeeds: DifferentialDriveWheelSpeeds
        get() {
            return DifferentialDriveWheelSpeeds(
                talonFXFrontLeft.selectedSensorVelocity.toDouble() / 4096 * 2 * PI * Constants.Chassis.WHEEL_RADIUS_METERS * 10,
                talonFXFrontRight.selectedSensorVelocity.toDouble() / 4096 * 2 * PI * Constants.Chassis.WHEEL_RADIUS_METERS * 10
            )
        }

    private val drive = DifferentialDrive(talonFXFrontLeft, talonFXFrontRight)

    private val ahrs = AHRS(SPI.Port.kMXP).apply {
        calibrate()
    }

    var generalMotionCharacteristics = MotionCharacteristics(ControlMode.DutyCycle, dutyCycleConfig = DutyCycleConfig(0.5))

    val odometry = DifferentialDriveOdometry(Rotation2d.fromDegrees(heading))

    fun resetOdometry(pose: Pose2d) {
        odometry.resetPosition(pose, Rotation2d.fromDegrees(heading))
    }

    val heading: Double
        get() {
            return ahrs.angle.IEEErem(360.0)
        }

    fun resetHeading() {
        ahrs.reset()
    }

    val turnRate: Double
        get() {
            return ahrs.rate
        }

    val pose: Pose2d
        get() {
            return odometry.poseMeters
        }

    init {
        defaultCommand = ChassisRunJoystick()

        Shuffleboard.getTab("Chassis").add(talonFXFrontRight)
        Shuffleboard.getTab("Chassis").add(talonFXFrontLeft)
        Shuffleboard.getTab("Chassis").add(talonFXBackRight)
        Shuffleboard.getTab("Chassis").add(talonFXBackLeft)

        Shuffleboard.getTab("Chassis").add(this)
    }

    override fun periodic() {
        odometry.update(Rotation2d(heading), leftPosition, rightPosition)
    }

    fun joystickDrive(x: Double, z: Double) {
        drive.curvatureDrive(
            x * generalMotionCharacteristics.dutyCycleConfig!!.dutyCycle,
            z * generalMotionCharacteristics.dutyCycleConfig!!.dutyCycle, true
        )
    }

    fun tankDrive(left: Double, right: Double) {
        drive.tankDrive(left, right)
    }
}
