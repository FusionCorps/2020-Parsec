package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants

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

    var driveSpd: Double = 0.5

    override fun periodic() {
    }

    fun joystick_drive(x: Double, z: Double) {
        drive.curvatureDrive(x * driveSpd, z * driveSpd, true)
    }
}
