package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.fusion.motion.FusionTalonFX
import mu.KotlinLogging

object Shooter : SubsystemBase() {
    private val talonFXTop = FusionTalonFX(Constants.Shooter.ID_TALONFX_TOP).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        setInverted(TalonFXInvertType.Clockwise)

        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10)
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10)

        selectProfileSlot(0, 0)

        kF = Constants.Shooter.kF
        kP = Constants.Shooter.kP
        kI = Constants.Shooter.kI
        kD = Constants.Shooter.kD

        selectedSensorPosition = 0
    }
    private val talonFXBottom = FusionTalonFX(Constants.Shooter.ID_TALONFX_BOTTOM).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        selectedSensorPosition = 0

        follow(talonFXTop)
        setInverted(TalonFXInvertType.OpposeMaster)
    }

    private val logger = KotlinLogging.logger("Shooter")

    val velocity: Int
        get() {
            return talonFXTop.getSelectedSensorVelocity(0)
        }

    override fun periodic() {
    }

    fun setShooter(controlMode: TalonFXControlMode, value: Double) {
        talonFXTop.set(controlMode, value)
//        talonFXTop.set(TalonFXControlMode.PercentOutput, 0.7)
    }
}
