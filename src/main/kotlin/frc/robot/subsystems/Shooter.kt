package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.FPIDConfig
import frc.robot.fusion.motion.FTalonFX
import frc.robot.fusion.motion.FollowerConfig
import frc.robot.fusion.motion.MotionCharacteristics
import frc.robot.fusion.motion.MotionConfig
import frc.robot.fusion.motion.MotorID
import frc.robot.fusion.motion.MotorModel

object Shooter : SubsystemBase() {
    private val talonFXTop = FTalonFX(MotorID(Constants.Shooter.ID_TALONFX_TOP, "ShooterTalonT", MotorModel.TalonFX)).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        setInverted(TalonFXInvertType.Clockwise)

        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10)
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10)

        selectProfileSlot(0, 0)

        control(FPIDConfig(Constants.Shooter.kF, Constants.Shooter.kP, Constants.Shooter.kI, Constants.Shooter.kD))

        selectedSensorPosition = 0
    }
    private val talonFXBottom = FTalonFX(MotorID(Constants.Shooter.ID_TALONFX_BOTTOM, "ShooterTalonB", MotorModel.TalonFX)).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        selectedSensorPosition = 0

        control(ControlMode.Follower, FollowerConfig(talonFXTop))
        setInverted(TalonFXInvertType.OpposeMaster)
    }

    val motionCharacteristics: MotionCharacteristics
        get() {
            return talonFXTop.motionCharacteristics
        }

    val velocity: Int
        get() {
            return talonFXTop.getSelectedSensorVelocity(0)
        }

    fun control(vararg config: MotionConfig) {
        talonFXTop.control(*config)
    }

    init {
        Shuffleboard.getTab("Shooter").add(talonFXTop)
        Shuffleboard.getTab("Shooter").add(this)
    }
}
