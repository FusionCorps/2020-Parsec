package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.DigitalOutput
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.indexer.IndexerManage
import mu.KotlinLogging

object Indexer : SubsystemBase() {
    private val logger = KotlinLogging.logger("Indexer")

    private val talonFXBelt = WPI_TalonFX(Constants.Indexer.ID_TALONFX).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        setInverted(TalonFXInvertType.Clockwise)

        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10)
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10)

        selectProfileSlot(0, 0)
        config_kF(0, Constants.Indexer.kF)
        config_kP(0, Constants.Indexer.kP)
        config_kI(0, Constants.Indexer.kI)
        config_kD(0, Constants.Indexer.kD)

        configMotionCruiseVelocity(Constants.Indexer.VELOCITY)
        configMotionAcceleration(Constants.Indexer.ACCELERATION)

        selectedSensorPosition = 0
    }

    private val frontSensorRX = DigitalInput(Constants.Indexer.ID_FRONT_SENSOR_RX)
    private val frontSensorTX = DigitalOutput(Constants.Indexer.ID_FRONT_SENSOR_TX).apply {
        set(true)
    }

    private val topSensorRX = DigitalInput(Constants.Indexer.ID_TOP_SENSOR_TX)

    init {
        defaultCommand = IndexerManage(this)
    }

    fun setBelt(controlMode: TalonFXControlMode, value: Double) {
//        talonFXBelt.set(controlMode, value)
        talonFXBelt.set(TalonFXControlMode.PercentOutput, 0.0)
    }

    fun getCurrentPosition(): Double {
        return talonFXBelt.selectedSensorPosition.toDouble()
    }

    fun isBallFront(): Boolean {
        return !frontSensorRX.get()
    }

    fun isBallTop(): Boolean {
        return false
    }
}
