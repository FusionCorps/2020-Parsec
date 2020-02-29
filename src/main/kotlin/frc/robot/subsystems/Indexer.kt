package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.DigitalOutput
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.indexer.IndexerManage
import frc.robot.fusion.motion.AssistedMotionConfig
import frc.robot.fusion.motion.FPIDConfig
import frc.robot.fusion.motion.FTalonFX
import frc.robot.fusion.motion.MotionCharacteristics
import frc.robot.fusion.motion.MotionConfig
import frc.robot.fusion.motion.MotorID
import frc.robot.fusion.motion.MotorModel
import mu.KotlinLogging

object Indexer : SubsystemBase() {
    private val logger = KotlinLogging.logger(this.name)

    // Motor controller
    private val talonFXBelt = FTalonFX(MotorID(Constants.Indexer.ID_TALONFX, "talonFXBelt", MotorModel.TalonFX)).apply {
        subsystem = "Indexer"

        configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor)

        setInverted(TalonFXInvertType.Clockwise)

        setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10)
        setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10)

        selectProfileSlot(0, 0)

        control(AssistedMotionConfig(Constants.Indexer.VELOCITY_INITIAL, Constants.Indexer.ACCELERATION_INITIAL), FPIDConfig(Constants.Indexer.kF_INITIAL, Constants.Indexer.kP_INITIAL, Constants.Indexer.kI_INITIAL, Constants.Indexer.kD_INITIAL))

        selectedSensorPosition = 0
    }

    val motionCharacteristics: MotionCharacteristics
        get() {
            return talonFXBelt.motionCharacteristics
        }
    val beltPosition: Int
        get() {
            return talonFXBelt.selectedSensorPosition
        }
    val beltVelocity: Int
        get() {
            return talonFXBelt.getActiveTrajectoryVelocity(0)
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
        defaultCommand = IndexerManage()

        Shuffleboard.getTab("Indexer").add(talonFXBelt)
    }

    fun control(vararg config: MotionConfig) {
        talonFXBelt.control(*config)
    }
}
