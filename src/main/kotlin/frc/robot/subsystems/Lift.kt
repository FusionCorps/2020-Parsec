package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.revrobotics.CANPIDController
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.ControlType
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import java.lang.Math.atan
import java.lang.Math.sin

object Lift : SubsystemBase() { // Important note: Spark Max Encoders count 4096 ticks per rev by default
    private val talonSRXExtend = WPI_TalonSRX(Constants.Lift.ID_TALONSRX_EXTEND).apply { // Higher speed motor
        setInverted(InvertType.InvertMotorOutput)

        config_kP(0, 0.5)
        config_kI(0, 0.0)
        config_kD(0, 0.0)

        configMotionCruiseVelocity(20000)
        configMotionAcceleration(10000)

        selectedSensorPosition = 0
    }

    private val sparkMaxRetract = CANSparkMax(Constants.Lift.ID_SPARKMAX_RETRACT, CANSparkMaxLowLevel.MotorType.kBrushless)

    private val retractPID = CANPIDController(sparkMaxRetract).apply {
        // 1 RPM is 34.133 TalonEncoderTicks/second

        setSmartMotionMaxAccel(292.97, 0) // RPM per sec
        setSmartMotionAllowedClosedLoopError(15.0, 0)
        setSmartMotionMaxVelocity(585.943, 0) // RPM

        p = 0.5
        i = 0.0
        d = 0.0
        ff = 0.0

//        setFeedbackDevice(CANSparkMax(Constants.Lift.ID_SPARKMAX_RETRACT, CANSparkMaxLowLevel.MotorType.kBrushless).getEncoder())
    }

    val extendVelocity: Int
        get() {
            return talonSRXExtend.selectedSensorVelocity
        }

    val retractVelocity: Double
        get() {
            return sparkMaxRetract.get()
        }

    val motorPosition: Int
        get() {
            return talonSRXExtend.selectedSensorPosition
        }

    fun setExtend(control_mode: TalonSRXControlMode = TalonSRXControlMode.Velocity, value: Double) {
        talonSRXExtend.set(control_mode, value)
    }

    fun extendStop() {
        talonSRXExtend.stopMotor()
    }

    fun setRetractPID(target: Double, control_type: ControlType = ControlType.kVelocity) {
        retractPID.setReference(target, control_type)
    }

    fun location_calculator(reading1: Double, reading2: Double, m_6672: Int, m_1: Int, m_2: Int, m_bar: Int, delta_sensor: Double): Double {
        val alpha = atan(0.66/1.41)
        val theta = atan((reading1-reading2)/delta_sensor)

        val distance = (0.66*(reading1-reading2)*(m_1+m_2+m_bar)/(m_6672*delta_sensor))
        val height = 2.83 - 1.56*sin(alpha+theta) + (1.41 + distance)* sin(theta)
        return height
    }
}
