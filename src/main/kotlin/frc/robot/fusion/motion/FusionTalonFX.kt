package frc.robot.fusion.motion

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder

class FusionTalonFX(deviceNumber: Int) : WPI_TalonFX(deviceNumber) {
    var position: Int
        set(value) {
            this.selectedSensorPosition = value
        }
        get() {
            return this.selectedSensorPosition
        }
    var kF: Double = 0.0
        set(value) {
            this.config_kF(0, value)
            field = value
        }
    var kP: Double = 0.0
        set(value) {
            this.config_kP(0, value)
            field = value
        }
    var kI: Double = 0.0
        set(value) {
            this.config_kI(0, value)
            field = value
        }
    var kD: Double = 0.0
        set(value) {
            this.config_kD(0, value)
            field = value
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

    override fun initSendable(builder: SendableBuilder?) {
        builder!!.setSmartDashboardType("RobotPreferences")

        builder.setSafeState(this::stopMotor)

        builder.addDoubleProperty("kF", { kF }, { x: Double -> kF = x })
        builder.addDoubleProperty("kP", { kP }, { x: Double -> kP = x })
        builder.addDoubleProperty("kI", { kI }, { x: Double -> kI = x })
        builder.addDoubleProperty("kD", { kD }, { x: Double -> kD = x })

        builder.getEntry("kF").setPersistent()
        builder.getEntry("kP").setPersistent()
        builder.getEntry("kI").setPersistent()
        builder.getEntry("kD").setPersistent()

        builder.addDoubleProperty("Position", { position.toDouble() }, { x: Double -> position = x.toInt() })
        builder.addDoubleProperty("Velocity", { targetVelocity.toDouble() }, { x: Double -> targetVelocity = x.toInt() })
        builder.addDoubleProperty("Acceleration", { targetAcceleration.toDouble() }, { x: Double -> targetAcceleration = x.toInt() })

        builder.getEntry("Position").setPersistent()
        builder.getEntry("Velocity").setPersistent()
        builder.getEntry("Acceleration").setPersistent()
    }
}
