package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.TalonFXInvertType
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj.DigitalOutput
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.indexer.IndexerManage
import frc.robot.fusion.motion.FPIDCharacteristics
import mu.KotlinLogging

object Indexer : SubsystemBase() {
    private val logger = KotlinLogging.logger("Indexer")

    // Motor controller
    private val talonFXBelt = WPI_TalonFX(Constants.Indexer.ID_TALONFX).apply {
        name = "TalonFX Belt"
        subsystem = "Indexer"

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

        configAllowableClosedloopError(0, 15) // Tolerance to stop oscillations

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
    var beltAcceleration: Int = Constants.Indexer.VELOCITY
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

//        // Update talonFXBelt FPID values
//        val iNetworkTables = NetworkTableInstance.getDefault()
//
//        val indexerTab = Shuffleboard.getTab("Indexer")
//
//        indexerTab.add(talonFXBelt)
//
//        val indexerFPIDList = indexerTab
//            .getLayout("Belt Motion", "List Layout")
//            .withPosition(0, 0)
//            .withSize(2, 3)
//
//        indexerFPIDList.add("kF", beltFPID.kF)
//        indexerFPIDList.add("kP", beltFPID.kP)
//        indexerFPIDList.add("kI", beltFPID.kI)
//        indexerFPIDList.add("kD", beltFPID.kD)
//        indexerFPIDList.add("Velocity", beltVelocity)
//        indexerFPIDList.add("Acceleration", beltAcceleration)
//
//        val indexerTable = iNetworkTables.getTable("Shuffleboard").getSubTable("Indexer")
//
//        val listsToAdd = mapOf<String, (Double) -> Unit>("kF" to { x -> beltFPID = FPIDCharacteristics(x, beltFPID.kP, beltFPID.kI, beltFPID.kD)})
//        indexerTable.getSubTable("Belt Motion")
//            .addListener(
//                { event: EntryNotification ->
//                    this.logger.info { "Received event $event" }
//                    when (event.entry.toString()) {
//                        "kF" -> beltFPID = FPIDCharacteristics(event.value.double, beltFPID.kP, beltFPID.kI, beltFPID.kD)
//                        "kP" -> beltFPID = FPIDCharacteristics(beltFPID.kF, event.value.double, beltFPID.kI, beltFPID.kD)
//                        "kI" -> beltFPID = FPIDCharacteristics(beltFPID.kF, beltFPID.kP, event.value.double, beltFPID.kD)
//                        "kD" -> beltFPID = FPIDCharacteristics(beltFPID.kF, beltFPID.kP, beltFPID.kI, event.value.double)
//                        "Velocity" -> beltVelocity = event.value.double.toInt()
//                        "Acceleration" -> beltAcceleration = event.value.double.toInt()
//                    }
//                },
//                EntryListenerFlags.kNew or EntryListenerFlags.kUpdate
//            )
    }

    // Methods
    fun setBelt(controlMode: TalonFXControlMode, value: Double) {
        talonFXBelt.set(controlMode, value)
    }
}
