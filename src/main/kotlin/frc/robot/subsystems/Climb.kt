package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj.Talon
import frc.robot.Constants
import frc.robot.commands.climb.ClimbRun
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Climb: SubsystemBase() {
    private val climbHighSpeedMotor = WPI_TalonFX(Constants.Climb.ID_HIGHSPEEDTALON).apply {
        config_kP(0, 0.5)
        config_kI(0, 0.0)
        config_kD(0, 0.0)

        configMotionCruiseVelocity(20000)
        configMotionAcceleration(10000)
        configMotionSCurveStrength(1)
        // Remember to add feed forward looping later
    }

    var currentHighSpeedMotorSpeed = 0.0

    private val climbHighTorqueMotor = WPI_TalonFX(Constants.Climb.ID_HIGHTORQUETALON).apply {
        config_kP(0, 0.5)
        config_kI(0, 0.5)
        config_kD(0, 0.5)

        configMotionCruiseVelocity(20000)
        configMotionAcceleration(10000)
        configMotionSCurveStrength(1)
    }

    var currentHighTorqueMotorSpeed = 0.0

    private val clawMotor = WPI_TalonFX(Constants.Climb.ID_CLAW)

    init {
        defaultCommand = ClimbRun(this)
    }

    fun setHighSpeedMotor(control_mode: TalonFXControlMode = TalonFXControlMode.Velocity, value: Double) {
        climbHighSpeedMotor.set(control_mode, value)
    }

    fun setHighTorqueMotor(control_mode: TalonFXControlMode = TalonFXControlMode.Velocity, value: Double) {
        climbHighTorqueMotor.set(control_mode, value)
    }

    fun offHighSpeedMotor() {
        climbHighSpeedMotor.stopMotor()
    }

    fun offHighTorqueMotor() {
        climbHighTorqueMotor.stopMotor()
    }

    fun getHighSpeedMotor(): Double {
        return climbHighSpeedMotor.get()
    }

    fun getHighTorqueMotor(): Double {
        return climbHighTorqueMotor.get()
    }

    fun setClaw(control_mode: TalonFXControlMode, value: Double) {
        clawMotor.set(control_mode, value)
    }

    fun getClaw(): Double {
        return clawMotor.get()
    }

}

