package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import com.ctre.phoenix.motorcontrol.can.TalonFX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import mu.KotlinLogging

object Shooter : SubsystemBase() {
    private val talonFXTop = TalonFX(Constants.Shooter.ID_TALONFX_TOP).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        setInverted(TalonFXInvertType.Clockwise)

        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10)
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10)

        selectProfileSlot(0, 0)
        config_kF(0, Constants.Shooter.kF)
        config_kP(0, Constants.Shooter.kP)
        config_kI(0, Constants.Shooter.kI)
        config_kD(0, Constants.Shooter.kD)

        selectedSensorPosition = 0
    }
    private val talonFXBottom = TalonFX(Constants.Shooter.ID_TALONFX_BOTTOM).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        selectedSensorPosition = 0

        follow(talonFXTop)
        setInverted(TalonFXInvertType.OpposeMaster)
    }

    private val logger = KotlinLogging.logger("Shooter")

    val velocity: Int
        get() {
            return talonFXTop.getSelectedSensorVelocity()
        }

    fun setShooter(controlMode: TalonFXControlMode, value: Double) {
        talonFXTop.set(controlMode, value)
    }
}
