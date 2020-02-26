package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.chassis.ChassisJoystickDrive
import mu.KotlinLogging

object Chassis : SubsystemBase() {
    // Motor Controllers
    private val talonFXFrontLeft = WPI_TalonFX(Constants.Chassis.ID_TALONFX_F_L).apply {
        setInverted(TalonFXInvertType.Clockwise)
    }
    private val talonFXBackLeft = WPI_TalonFX(Constants.Chassis.ID_TALONFX_B_L).apply {
        setInverted(TalonFXInvertType.FollowMaster)
        follow(talonFXFrontLeft)
    }
    private val talonFXFrontRight = WPI_TalonFX(Constants.Chassis.ID_TALONFX_F_R).apply {
        setInverted(TalonFXInvertType.CounterClockwise)
    }
    private val talonFXBackRight = WPI_TalonFX(Constants.Chassis.ID_TALONFX_B_R).apply {
        setInverted(TalonFXInvertType.FollowMaster)
        follow(talonFXFrontRight)
    }

    private val drive = DifferentialDrive(talonFXFrontLeft, talonFXFrontRight)

    private val logger = KotlinLogging.logger("Chassis")

    var driveSpd: Double = 0.5

    init {
        defaultCommand = ChassisJoystickDrive(this)

        Shuffleboard.getTab("Chassis").add(talonFXFrontRight)
        Shuffleboard.getTab("Chassis").add(talonFXFrontLeft)
        Shuffleboard.getTab("Chassis").add(talonFXBackRight)
        Shuffleboard.getTab("Chassis").add(talonFXBackLeft)
    }

    override fun periodic() {
    }

    fun joystickDrive(x: Double, z: Double) {
        drive.curvatureDrive(x * driveSpd, z * driveSpd, true)
    }
}
