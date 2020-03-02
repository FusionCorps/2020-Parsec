package frc.robot.fusion.motion

import com.ctre.phoenix.motorcontrol.IMotorController
import com.revrobotics.CANSparkMax

interface MotionConfig

data class MotionCharacteristics(
    var controlMode: ControlMode = ControlMode.Disabled,
    var fpidConfig: FPIDConfig? = null,
    var dutyCycleConfig: DutyCycleConfig? = null,
    var positionConfig: PositionConfig? = null,
    var velocityConfig: VelocityConfig? = null,
    var assistedMotionConfig: AssistedMotionConfig? = null,
    var followerConfig: FollowerConfig? = null
) {
    fun update(vararg configuration: MotionConfig) {
        configuration.forEach {
            when (it) {
                is ControlMode -> controlMode = it
                is FPIDConfig -> {
                    fpidConfig = FPIDConfig(
                        it.f, it.p, it.i, it.d, it.integralZone ?: fpidConfig?.integralZone,
                        it.allowedError ?: fpidConfig?.allowedError, it.maxIntegral ?: fpidConfig?.maxIntegral,
                        it.maxOutput ?: fpidConfig?.maxOutput, it.period ?: fpidConfig?.period
                    )
                }
                is DutyCycleConfig -> dutyCycleConfig = it
                is PositionConfig -> positionConfig = PositionConfig(
                    it.targetPosition,
                    it.sensorPosition ?: positionConfig?.sensorPosition
                )
                is VelocityConfig -> velocityConfig = it
                is AssistedMotionConfig -> assistedMotionConfig = it
                is FollowerConfig -> followerConfig = it
                else -> throw IllegalArgumentException()
            }
        }
    }
}

enum class ControlMode : MotionConfig {
    Disabled,
    Velocity,
    DutyCycle,
    Position,
    AssistedMotion,
    Follower
}

data class FPIDConfig(
    var f: Double = 0.0,
    var p: Double = 0.0,
    var i: Double = 0.0,
    var d: Double = 0.0,
    var integralZone: Int? = null,
    var allowedError: Int? = null,
    var maxIntegral: Double? = null,
    var maxOutput: Double? = null,
    var period: Int? = null
) : MotionConfig
// Add ClosedloopConfig for position or velocity targets?
data class DutyCycleConfig(var dutyCycle: Double) : MotionConfig
data class VelocityConfig(var velocity: Int) : MotionConfig
data class PositionConfig(var targetPosition: Int, var sensorPosition: Int? = null) : MotionConfig
data class AssistedMotionConfig(var acceleration: Int, var curveStrength: Int = 0) : MotionConfig
data class FollowerConfig(val ctreMaster: IMotorController? = null, val revMaster: CANSparkMax? = null) : MotionConfig
