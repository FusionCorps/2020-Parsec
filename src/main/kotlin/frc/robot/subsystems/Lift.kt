package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.climb.ClimbRun

object Lift : SubsystemBase() {
    private val talonFXExtend = TalonFX(Constants.Lift.ID_TALONFX_EXTEND).apply {  // Higher speed motor
        config_kP(0, 0.5)
        config_kI(0, 0.0)
        config_kD(0, 0.0)

        configMotionCruiseVelocity(20000)
        configMotionAcceleration(10000)
        configMotionSCurveStrength(1)

        // TODO - Add feed-forward looping

        setSelectedSensorPosition(0)
    }

    private val talonFXRetract = TalonFX(Constants.Lift.ID_TALONFX_RETRACT).apply {  // Higher torque motor
        config_kP(0, 0.5)
        config_kI(0, 0.5)
        config_kD(0, 0.5)

        configMotionCruiseVelocity(20000)
        configMotionAcceleration(10000)
        configMotionSCurveStrength(1)

        // TODO - Add feed-forward looping

        setSelectedSensorPosition(0)
    }

    init {
        defaultCommand = ClimbRun(this)
    }

    val extendVelocity: Int
        get() {
            return talonFXExtend.getSelectedSensorVelocity()
        }

    val retractVelocity: Int
        get() {
            return talonFXRetract.getSelectedSensorVelocity()
        }

    val motorPosition: Int
        get() {
            return talonFXExtend.getSelectedSensorPosition()
        }

    fun setExtend(control_mode: TalonFXControlMode = TalonFXControlMode.Velocity, value: Double) {
        talonFXExtend.set(control_mode, value)
    }

    fun setRetract(control_mode: TalonFXControlMode = TalonFXControlMode.Velocity, value: Double) {
        talonFXRetract.set(control_mode, value)
    }

}
