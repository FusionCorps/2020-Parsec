package frc.robot.fusion.motion

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX

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
            this.config_kF(0, value)
            field = kP
        }
    var kI: Double = 0.0
        set(value) {
            this.config_kF(0, value)
            field = kI
        }
    var kD: Double = 0.0
        set(value) {
            this.config_kF(0, value)
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
}
