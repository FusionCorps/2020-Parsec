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
            field = kF
        }
    var kP: Double = 0.0
        set(value) {
            this.config_kP(0, value)
            field = kP
        }
    var kI: Double = 0.0
        set(value) {
            this.config_kI(0, value)
            field = kI
        }
    var kD: Double = 0.0
        set(value) {
            this.config_kD(0, value)
            field = kD
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
        builder!!.setSmartDashboardType("List")

        builder.setSafeState(this::stopMotor)

        builder.addDoubleArrayProperty(
            "FPID",
            {
                DoubleArray(4).apply {
                    set(0, kF)
                    set(1, kP)
                    set(2, kI)
                    set(3, kD)
                }
            },
            { x: DoubleArray ->
                kF = x[0]
                kP = x[1]
                kI = x[2]
                kD = x[3]
            }
        )
        builder.addDoubleProperty("Position", { position.toDouble() }, { x: Double -> position = x.toInt() })
        builder.addDoubleProperty("Velocity", { targetVelocity.toDouble() }, { x: Double -> targetVelocity = x.toInt() })
        builder.addDoubleProperty("Acceleration", { targetAcceleration.toDouble() }, { x: Double -> targetAcceleration = x.toInt() })
    }
}
