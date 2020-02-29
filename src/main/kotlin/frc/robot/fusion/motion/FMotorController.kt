package frc.robot.fusion.motion

import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import com.ctre.phoenix.motorcontrol.ControlMode as CTREControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj.Sendable
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder

enum class MotorModel(val model: String) {
    TalonFX("Talon FX"),
    TalonSRX("Talon SRX"),
    VictorSPX("Victor SPX"),
    CANSparkMax("CAN Spark Max")
}

interface FCTREMotor : Sendable {
    val motorID: MotorID
    var motionCharacteristics: MotionCharacteristics

    fun control(vararg config: MotionConfig)

    fun control(motionCharacteristics: MotionCharacteristics, motor: BaseMotorController, vararg config: MotionConfig) {
        motionCharacteristics.update(*config)

        when (motionCharacteristics.controlMode) {
            ControlMode.Disabled -> motor.set(CTREControlMode.Disabled, 0.0)
            ControlMode.Position, ControlMode.Velocity -> {
                motionCharacteristics.fpidConfig?.let {
                    motor.run {
                        selectProfileSlot(0, 0)

                        config_kF(0, it.f)
                        config_kP(0, it.p)
                        config_kI(0, it.i)
                        config_kD(0, it.d)
                    }
                } ?: throw IllegalStateException("Tried to configure FPID with null configuration!")

                if (motionCharacteristics.controlMode == ControlMode.Position) {
                    motor.set(CTREControlMode.Position, motionCharacteristics.positionConfig!!.targetPosition.toDouble())
                } else {
                    motor.set(CTREControlMode.Velocity, motionCharacteristics.assistedMotionConfig!!.velocity.toDouble())
                }
            }
            ControlMode.AssistedMotion -> {
                motionCharacteristics.assistedMotionConfig?.let {
                    motor.run {
                        configMotionAcceleration(it.acceleration)
                        configMotionCruiseVelocity(it.velocity)
                        configMotionSCurveStrength(it.curveStrength)
                    }
                } ?: throw IllegalStateException("Tried to configure AssistedMotion with null configuration!")

                motor.set(CTREControlMode.MotionMagic, motionCharacteristics.positionConfig!!.targetPosition.toDouble())
            }
            ControlMode.DutyCycle -> motionCharacteristics.dutyCycleConfig?.let { motor.set(CTREControlMode.PercentOutput, it.dutyCycle) } ?: throw IllegalStateException("Tried to configure DutyCycle with null configuration!")
            ControlMode.Follower -> {
                motionCharacteristics.followerConfig?.let {
                    motor.follow(it.ctreMaster ?: throw IllegalStateException("Tried to configure Follower with null configuration!"))
                } ?: throw IllegalStateException()
            }
        }
    }

    override fun initSendable(builder: SendableBuilder?) {
        builder!!.setSmartDashboardType("RobotPreferences")

        builder.addDoubleProperty(
                "f", { motionCharacteristics.fpidConfig?.f ?: 0.0 },
                { x: Double -> motionCharacteristics.fpidConfig?.f = x }
        )
        builder.addDoubleProperty(
                "p", { motionCharacteristics.fpidConfig?.p ?: 0.0 },
                { x: Double -> motionCharacteristics.fpidConfig?.p = x }
        )
        builder.addDoubleProperty(
                "i", { motionCharacteristics.fpidConfig?.i ?: 0.0 },
                { x: Double -> motionCharacteristics.fpidConfig?.i = x }
        )
        builder.addDoubleProperty(
                "d", { motionCharacteristics.fpidConfig?.d ?: 0.0 },
                { x: Double -> motionCharacteristics.fpidConfig?.d = x }
        )

        builder.getEntry("f").setPersistent()
        builder.getEntry("p").setPersistent()
        builder.getEntry("i").setPersistent()
        builder.getEntry("d").setPersistent()

        builder.addDoubleProperty(
                "Velocity",
                { motionCharacteristics.assistedMotionConfig?.velocity?.toDouble() ?: 0.0 },
                { x: Double -> motionCharacteristics.assistedMotionConfig?.velocity = x.toInt() }
        )
        builder.addDoubleProperty(
                "Position",
                { motionCharacteristics.positionConfig?.targetPosition?.toDouble() ?: 0.0 },
                { x: Double -> motionCharacteristics.assistedMotionConfig?.velocity = x.toInt() }
        )
        builder.addDoubleProperty(
                "Acceleration",
                { motionCharacteristics.assistedMotionConfig?.acceleration?.toDouble() ?: 0.0 },
                { x: Double -> motionCharacteristics.assistedMotionConfig?.velocity = x.toInt() }
        )

        builder.getEntry("Velocity").setPersistent()
        builder.getEntry("Acceleration").setPersistent()
        builder.getEntry("Position").setPersistent()
    }
}

data class MotorID(val id: Int, val name: String, val model: MotorModel) {
    companion object {
        var motorIDs = mutableMapOf<Int, MotorID>()
    }

    init {
        if (motorIDs[id] != null) {
            throw IllegalArgumentException()
        }
        motorIDs[id] = this
    }
}

class FTalonFX(id: MotorID) : WPI_TalonFX(id.id), FCTREMotor {
    override val motorID = id
    override var motionCharacteristics = MotionCharacteristics()

    override fun control(vararg config: MotionConfig) {
        control(motionCharacteristics, this, *config)
    }

    override fun initSendable(builder: SendableBuilder?) {
        super<FCTREMotor>.initSendable(builder)
    }

    init {
        configFactoryDefault()
        name = id.name
    }
}

class FTalonSRX(id: MotorID) : WPI_TalonSRX(id.id), FCTREMotor {
    override val motorID = id
    override var motionCharacteristics = MotionCharacteristics()

    override fun control(vararg config: MotionConfig) {
        control(motionCharacteristics, this, *config)
    }

    override fun initSendable(builder: SendableBuilder?) {
        super<FCTREMotor>.initSendable(builder)
    }

    init {
        configFactoryDefault()
        name = id.name
    }
}

class FVictorSPX(id: MotorID) : WPI_VictorSPX(id.id), FCTREMotor {
    override val motorID = id
    override var motionCharacteristics = MotionCharacteristics()

    override fun control(vararg config: MotionConfig) {
        control(motionCharacteristics, this, *config)
    }

    override fun initSendable(builder: SendableBuilder?) {
        super<FCTREMotor>.initSendable(builder)
    }

    init {
        configFactoryDefault()
        name = id.name
    }
}
