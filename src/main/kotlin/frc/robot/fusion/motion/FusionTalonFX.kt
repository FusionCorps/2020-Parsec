package frc.robot.fusion.motion

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder

class FusionTalonFX(deviceNumber: Int) : WPI_TalonFX(deviceNumber) {
    private val frameInfo = mutableMapOf<StatusFrameEnhanced, FrameInfo>()

    var sensorPosition: Int
        set(value) {
            this.selectedSensorPosition = value
        }
        get() {
            return this.selectedSensorPosition
        }
    var fpidCharacteristics = FPIDCharacteristics(0.0, 0.0, 0.0, 0.0)
        set(value) {
            if (field.f != value.f) {
                this.config_kF(0, value.f)
            }
            if (field.p != value.p) {
                this.config_kP(0, value.p)
            }
            if (field.i != value.i) {
                this.config_kI(0, value.i)
            }
            if (field.d != value.d) {
                this.config_kD(0, value.d)
            }

            field = value
        }
        get() {
            if (frameInfo[StatusFrameEnhanced.Status_13_Base_PIDF0]?.needsUpdate() == true) {
                val slotConfig = SlotConfiguration()
                this.getSlotConfigs(slotConfig)

                field = FPIDCharacteristics(slotConfig.kF, slotConfig.kP, slotConfig.kI, slotConfig.kD)
            }
            return field
        }
    var targetVelocity: Int = 0
        set(value) {
            this.configMotionCruiseVelocity(value)
            field = value
        }
    var targetAcceleration: Int = 0
        set(value) {
            this.configMotionAcceleration(value)
            field = value
        }

    fun setFrame(frame: StatusFrameEnhanced, period: Int) {
        this.setStatusFramePeriod(frame, period)
        frameInfo[frame] = FrameInfo(period)
    }

    override fun initSendable(builder: SendableBuilder?) {
        // Needs to set to this type to be editable
        builder!!.setSmartDashboardType("RobotPreferences")

        builder.setSafeState(this::stopMotor)

        builder.addDoubleProperty("f", { fpidCharacteristics.f }, { x: Double -> fpidCharacteristics.f = x })
        builder.addDoubleProperty("p", { fpidCharacteristics.p }, { x: Double -> fpidCharacteristics.p = x })
        builder.addDoubleProperty("i", { fpidCharacteristics.i }, { x: Double -> fpidCharacteristics.i = x })
        builder.addDoubleProperty("d", { fpidCharacteristics.d }, { x: Double -> fpidCharacteristics.d = x })

        builder.getEntry("f").setPersistent()
        builder.getEntry("p").setPersistent()
        builder.getEntry("i").setPersistent()
        builder.getEntry("d").setPersistent()

        builder.addDoubleProperty("Position", { sensorPosition.toDouble() }, { x: Double -> sensorPosition = x.toInt() })
        builder.addDoubleProperty("Velocity", { targetVelocity.toDouble() }, { x: Double -> targetVelocity = x.toInt() })
        builder.addDoubleProperty("Acceleration", { targetAcceleration.toDouble() }, { x: Double -> targetAcceleration = x.toInt() })

        builder.getEntry("Position").setPersistent()
        builder.getEntry("Velocity").setPersistent()
        builder.getEntry("Acceleration").setPersistent()
    }
}

data class FrameInfo(var period: Int, var lastUpdate: Long = 0) {
    fun needsUpdate(): Boolean {
        return System.currentTimeMillis() > (lastUpdate + period)
    }
}
