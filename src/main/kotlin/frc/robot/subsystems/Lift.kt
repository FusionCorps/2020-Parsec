package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.revrobotics.CANPIDController
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.lift.LiftRun

object Lift : SubsystemBase() { // Important note: Spark Max Encoders count 4096 ticks per rev by default
    private val talonSRXExtend = WPI_TalonSRX(Constants.Lift.ID_TALONFX_EXTEND).apply { // Higher speed motor
        config_kP(0, 0.5)
        config_kI(0, 0.0)
        config_kD(0, 0.0)

        configMotionCruiseVelocity(20000)
        configMotionAcceleration(10000)
        configMotionSCurveStrength(1)

        // TODO - Add feed-forward looping

        setSelectedSensorPosition(0)
    }

//    private val talonFXRetract = WPI_TalonFX(Constants.Lift.ID_TALONFX_RETRACT).apply { // Higher torque motor
//        config_kP(0, 0.5)
//        config_kI(0, 0.5)
//        config_kD(0, 0.5)
//
//        configMotionCruiseVelocity(20000)
//        configMotionAcceleration(10000)
//        configMotionSCurveStrength(1)
//
//        // TODO - Add feed-forward looping (F Constant)
//
//        setSelectedSensorPosition(0)
//    } This code is old

    private val sparkMaxRetract = CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless)

    private val retractPID = CANPIDController(sparkMaxRetract).apply {
        // 1 RPM is 34.133 TalonEncoderTicks/second

        setSmartMotionMaxAccel(292.97, 0) // RPM per sec
        setSmartMotionAllowedClosedLoopError(15.0, 0)
        setSmartMotionMaxVelocity(585.943, 0) // RPM

        setP(0.5)
        setI(0.0)
        setD(0.0)
        setFF(0.0)

        setFeedbackDevice(CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless).getEncoder())

        // TODO - Convert RPM to TalonEncoderTicks/second - Done :)
    }

//    private val pidRetract = PIDController(2.0, 0.0, 0.0)

    init {
        defaultCommand = LiftRun(this)
    }

    val extendVelocity: Int
        get() {
            return talonSRXExtend.getSelectedSensorVelocity()
        }

    val retractVelocity: Double
        get() {
            return sparkMaxRetract.get()
        }

    val motorPosition: Int
        get() {
            return talonSRXExtend.getSelectedSensorPosition()
        }

    fun setExtend(control_mode: TalonSRXControlMode = TalonSRXControlMode.Velocity, value: Double) {
        talonSRXExtend.set(control_mode, value)
    }

    fun extendOff() {
        talonSRXExtend.stopMotor()
    }

//    fun setRetractVelocity(velocity: Double) {
//        sparkMaxRetract.set(velocity)
//    }

    fun setRetractPID(target: Double, control_type: ControlType = ControlType.kVelocity) {
        retractPID.setReference(target, control_type)
    }
}
