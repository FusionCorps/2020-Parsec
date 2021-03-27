package frc.robot.fusion.motion

import com.ctre.phoenix.motorcontrol.ControlMode as CTREControlMode
import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import com.revrobotics.CANSparkMax
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj.Sendable
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry

enum class MotorModel(val model: String) { // Define the 4 motors we use
    TalonFX("Talon FX"),
    TalonSRX("Talon SRX"),
    VictorSPX("Victor SPX"),
    CANSparkMax("CAN Spark Max")
}

interface FMotorController<T> : Sendable { // Set up Motor controller base
    val motorID: MotorID
    val motionCharacteristics: MotionCharacteristics

    fun control(vararg config: MotionConfig)
    fun control(motionCharacteristics: MotionCharacteristics, motor: T, vararg config: MotionConfig) // Set motion configs

    override fun initSendable(builder: SendableBuilder?) {
        builder!!.setSmartDashboardType("RobotPreferences") // Set up data for dashboard

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
            { motionCharacteristics.velocityConfig?.velocity?.toDouble() ?: 0.0 },
            { x: Double -> motionCharacteristics.velocityConfig?.velocity = x.toInt() }
        )
        builder.addDoubleProperty(
            "Position",
            { motionCharacteristics.positionConfig?.targetPosition?.toDouble() ?: 0.0 },
            { x: Double -> motionCharacteristics.positionConfig?.targetPosition = x.toInt() }
        )
        builder.addDoubleProperty(
            "Acceleration",
            { motionCharacteristics.assistedMotionConfig?.acceleration?.toDouble() ?: 0.0 },
            { x: Double -> motionCharacteristics.assistedMotionConfig?.acceleration = x.toInt() }
        )

        builder.getEntry("Velocity").setPersistent()
        builder.getEntry("Acceleration").setPersistent()
        builder.getEntry("Position").setPersistent()

        builder.addDoubleProperty(
            "DutyCycle",
            { motionCharacteristics.dutyCycleConfig?.dutyCycle ?: 0.0 },
            { x: Double -> motionCharacteristics.dutyCycleConfig?.dutyCycle = x }
        )

        builder.getEntry("DutyCycle").setPersistent()

        // Executes the necessary configuration methods to implement the motionCharacteristics
        builder.addBooleanProperty(
            "Update", { false },
            {
                control(ControlMode.Disabled)
                control(motionCharacteristics.controlMode)
            }
        )
//        builder.addBooleanProperty("Disabled", { motionCharacteristics.controlMode == ControlMode.Disabled }, { x: Boolean -> if (x) control(ControlMode.Disabled) })
    }
}

interface REVMotor : Sendable, FMotorController<CANSparkMax> { // Run motor class for CANSparkMax
    override fun control(motionCharacteristics: MotionCharacteristics, motor: CANSparkMax, vararg config: MotionConfig) {
        motionCharacteristics.update(*config)

        when (motionCharacteristics.controlMode) {
            ControlMode.Disabled -> motor.stopMotor() // Stop when disabled
            ControlMode.Position, ControlMode.Velocity -> { // Use PID to run to pos/velocity
                motionCharacteristics.fpidConfig?.let {
                    motor.pidController.run {
                        ff = it.f
                        p = it.p
                        i = it.i
                        d = it.d

                        it.allowedError?.let {
                            setSmartMotionAllowedClosedLoopError(it.toDouble(), 0)
                        }

                        if (motionCharacteristics.controlMode == ControlMode.Position) {
                            setReference(motionCharacteristics.positionConfig!!.targetPosition.toDouble(), ControlType.kPosition)
                        } else {
                            setReference(motionCharacteristics.velocityConfig!!.velocity.toDouble(), ControlType.kVelocity)
                        }
                    }
                } ?: throw IllegalStateException("Tried to configure Position with null configuration!")
            }
            ControlMode.AssistedMotion -> { // Motion w/ PID
                motionCharacteristics.assistedMotionConfig?.let {
                    motor.pidController.run {
                        setSmartMotionMaxAccel(motionCharacteristics.assistedMotionConfig!!.acceleration.toDouble(), 0)
                    }
                } ?: throw IllegalStateException("Tried to configure AssistedMotion with null assistedMotionConfig!")
                motionCharacteristics.velocityConfig?.let {// Run PID to Velocity
                    motor.pidController.run {
                        setSmartMotionMaxVelocity(motionCharacteristics.velocityConfig!!.velocity.toDouble(), 0)

                        setReference(motionCharacteristics.positionConfig!!.targetPosition.toDouble(), ControlType.kSmartMotion, 0)
                    }
                } ?: throw IllegalStateException("Tried to configure AssistedMotion with null velocityConfig!")
            }
            ControlMode.DutyCycle -> { // Run Duty Cycle
                motionCharacteristics.dutyCycleConfig?.let {
                    motor.set(motionCharacteristics.dutyCycleConfig!!.dutyCycle)
                }
            }
            ControlMode.Follower -> { // Run follower
                motionCharacteristics.followerConfig?.let {
                    if (motionCharacteristics.followerConfig!!.ctreMaster != null && motionCharacteristics.followerConfig!!.revMaster != null) {
                        throw IllegalArgumentException("Tried to use FollowerConfig with both CTRE and Rev configuration!")
                    } else if (motionCharacteristics.followerConfig!!.ctreMaster != null) {
                        motor.follow(CANSparkMax.ExternalFollower.kFollowerPhoenix, motionCharacteristics.followerConfig!!.ctreMaster!!.deviceID)
                    } else {
                        motor.follow(motionCharacteristics.followerConfig!!.revMaster!!)
                    }
                }
            }
        }
    }

    override fun initSendable(builder: SendableBuilder?) { // Dashboard sender
        super.initSendable(builder)
    }
}

interface FCTREMotor : Sendable, FMotorController<BaseMotorController> { // Running motor class for CTRE
    override fun control(motionCharacteristics: MotionCharacteristics, motor: BaseMotorController, vararg config: MotionConfig) {
        motionCharacteristics.update(*config)

        when (motionCharacteristics.controlMode) {
            ControlMode.Disabled -> motor.set(CTREControlMode.Disabled, 0.0) // Stop motor
            ControlMode.Position, ControlMode.Velocity -> { // Run PID to pos/vel
                motionCharacteristics.fpidConfig?.let {
                    motor.run {
                        selectProfileSlot(0, 0)

                        config_kF(0, it.f)
                        config_kP(0, it.p)
                        config_kI(0, it.i)
                        config_kD(0, it.d)
                    }
                } ?: throw IllegalStateException("Tried to configure FPID with null configuration!")

                if (motionCharacteristics.controlMode == ControlMode.Position) { // Run as postion
                    motor.set(CTREControlMode.Position, motionCharacteristics.positionConfig!!.targetPosition.toDouble())
                } else { // Catch case
                    motor.set(CTREControlMode.Velocity, motionCharacteristics.velocityConfig!!.velocity.toDouble())
                }
            }
            ControlMode.AssistedMotion -> { // Run with Motion S Curve
                motionCharacteristics.assistedMotionConfig?.let {
                    motor.run {
                        configMotionAcceleration(it.acceleration)
                        configMotionCruiseVelocity(motionCharacteristics.velocityConfig?.velocity ?: throw IllegalStateException("Tried to configure AssistedMotion with null Velocity configuration!"))
                        configMotionSCurveStrength(it.curveStrength)
                    }
                } ?: throw IllegalStateException("Tried to configure AssistedMotion with null configuration!")

                motor.set(CTREControlMode.MotionMagic, motionCharacteristics.positionConfig?.targetPosition?.toDouble() ?: throw IllegalStateException("Tried to configure AssistedMotion with null Position configuration!"))
            }
            // Duty Cycle
            ControlMode.DutyCycle -> motionCharacteristics.dutyCycleConfig?.let { motor.set(CTREControlMode.PercentOutput, it.dutyCycle) } ?: throw IllegalStateException("Tried to configure DutyCycle with null configuration!")
            ControlMode.Follower -> { // Run Follower
                motionCharacteristics.followerConfig?.let {
                    motor.follow(it.ctreMaster ?: throw IllegalStateException("Tried to configure Follower with null configuration!"))
                } ?: throw IllegalStateException("Tried to configure Follower with null configuration!")
            }
        }
    }

    override fun initSendable(builder: SendableBuilder?) { // Dashboard
        super.initSendable(builder)
    }
}

data class MotorID(val id: Int, val name: String, val model: MotorModel) { // Class to store motor data
    companion object {
        var motorIDs = mutableMapOf<Int, MotorID>()
    }

    init {
        if (motorIDs[id] != null) {
            throw IllegalArgumentException("A MotorController with that ID exists already!")
        }
        motorIDs[id] = this
    }
}

class FTalonFX(id: MotorID) : WPI_TalonFX(id.id), FCTREMotor { // Run Talon of CTRE base
    override val motorID = id
    override var motionCharacteristics = MotionCharacteristics()

    override fun control(vararg config: MotionConfig) {
        control(motionCharacteristics, this, *config)
    }

    override fun initSendable(builder: SendableBuilder?) {
        super<FCTREMotor>.initSendable(builder)

        builder!!.addDoubleProperty("SensorVelocity", { this.selectedSensorVelocity.toDouble() }, { })
        builder.addDoubleProperty("SensorPosition", { this.selectedSensorPosition.toDouble() }, { })
    }



    init {
        configFactoryDefault()
    }
}

class FTalonSRX(id: MotorID) : WPI_TalonSRX(id.id), FCTREMotor { // Run SRX off CTRE above
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
    }
}

class FVictorSPX(id: MotorID) : WPI_VictorSPX(id.id), FCTREMotor { // Run Victor with CTRE base
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
    }
}

class FCANSparkMax(id: MotorID, type: MotorType) : CANSparkMax(id.id, type), REVMotor { // Run CANSparkMax as above
    override val motorID = id
    override var motionCharacteristics = MotionCharacteristics()

    override fun control(vararg config: MotionConfig) {
        control(motionCharacteristics, this, *config)
    }

    override fun initSendable(builder: SendableBuilder?) {
        super.initSendable(builder)
    }

    init {
        restoreFactoryDefaults()
        SendableRegistry.add(this, motorID.name)
    }
}
