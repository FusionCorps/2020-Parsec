package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.indexer.IndexerManage
import frc.robot.fusion.motion.FPIDCharacteristics

object Indexer : SubsystemBase() {
    private val talonFXBelt = WPI_TalonFX(Constants.Indexer.ID_TALONFX).apply {
        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        setInverted(TalonFXInvertType.CounterClockwise)

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

    private val frontSensor = DigitalInput(Constants.Indexer.ID_FRONT_SENSOR)
    private val topSensor = DigitalInput(Constants.Indexer.ID_TOP_SENSOR)

    var characteristics = FPIDCharacteristics(0.0, 0.1, 0.0, 0.0)

    init {
        defaultCommand = IndexerManage(this)
    }

    fun setBelt(controlMode: TalonFXControlMode, value: Double) {
        talonFXBelt.set(controlMode, value)
    }

    fun getCurrentPosition(): Double {
        return talonFXBelt.selectedSensorPosition.toDouble()
    }

    fun isBallFront(): Boolean {
        return frontSensor.get()
    }

    fun isBallTop(): Boolean {
        return topSensor.get()
    }
}
