package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.DigitalOutput
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.indexer.IndexerManage
import frc.robot.fusion.motion.FPIDCharacteristics
import frc.robot.fusion.motion.FusionTalonFX
import mu.KotlinLogging

object Indexer : SubsystemBase() {
    private val logger = KotlinLogging.logger("Indexer")

    // Motor controller
    private val talonFXBelt = FusionTalonFX(Constants.Indexer.ID_TALONFX).apply {
        name = "TalonFX Belt"
        subsystem = "Indexer"

        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        setInverted(TalonFXInvertType.Clockwise)

        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10)
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10)

        selectProfileSlot(0, 0)

        kF = Constants.Indexer.kF_INITIAL
        kP = Constants.Indexer.kP_INITIAL
        kI = Constants.Indexer.kI_INITIAL
        kD = Constants.Indexer.kD_INITIAL

        targetVelocity = Constants.Indexer.VELOCITY_INITIAL
        targetAcceleration = Constants.Indexer.ACCELERATION_INITIAL

        selectedSensorPosition = 0
    }

    var beltPosition: Int
        set(value) {
            talonFXBelt.selectedSensorPosition = value
        }
        get() {
            return talonFXBelt.selectedSensorPosition
        }
    var beltFPID: FPIDCharacteristics
        set(value) {
            talonFXBelt.apply {
                config_kF(0, value.kF)
                config_kP(0, value.kP)
                config_kI(0, value.kI)
                config_kD(0, value.kD)
            }
            logger.info { "FPID -> $value" }
        }
        get() {
            val slotConfig = SlotConfiguration()
            talonFXBelt.getSlotConfigs(slotConfig)

            return FPIDCharacteristics(slotConfig.kF, slotConfig.kP, slotConfig.kI, slotConfig.kD)
        }
    var beltVelocity: Int
        set(value) {
            talonFXBelt.configMotionCruiseVelocity(value)
        }
        get() {
            return talonFXBelt.getActiveTrajectoryVelocity(0)
        }
    var beltAcceleration: Int = Constants.Indexer.VELOCITY_INITIAL
        set(value) {
            talonFXBelt.configMotionCruiseVelocity(value)
            field = value
        }

    // Ball IR Breakage Sensors
    private val frontSensorRX = DigitalInput(Constants.Indexer.ID_FRONT_SENSOR_RX)
    private val frontSensorTX = DigitalOutput(Constants.Indexer.ID_FRONT_SENSOR_TX)
        .apply {
            set(true)
        }

    private val topSensorRX = DigitalInput(Constants.Indexer.ID_TOP_SENSOR_RX)
    private val topSensorTX = DigitalOutput(Constants.Indexer.ID_TOP_SENSOR_TX).apply { set(true) }

    val isBallFront: Boolean
        get() {
            return !frontSensorRX.get()
        }
    val isBallTop: Boolean
        get() {
            return !topSensorRX.get()
        }

    // Instantiation
    init {
        defaultCommand = IndexerManage(this)
    }

    // Methods
    fun setBelt(controlMode: TalonFXControlMode, value: Double) {
        talonFXBelt.set(controlMode, value)
    }
}
