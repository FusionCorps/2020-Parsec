package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.lift.LiftRun

object Lift : SubsystemBase() {
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
//        // TODO - Add feed-forward looping
//
//        setSelectedSensorPosition(0)
//    } This code is old 

    private val sparkMaxRetract = CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless)

    private val pidRetract = PIDController(2.0, 0.0, 0.0)

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

    fun setRetractVelocity(velocity: Double) {
        sparkMaxRetract.set(velocity)
    }

    fun setRetractPIDPos(target_pos: Double, pid_tolerance: Double) {
        pidRetract.setTolerance(pid_tolerance)
        while (!pidRetract.atSetpoint()) {
            sparkMaxRetract.set(pidRetract.calculate(sparkMaxRetract.encoder.getPosition(), target_pos)) }
    }
}
