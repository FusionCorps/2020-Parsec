package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
// import frc.robot.commands.spinner.SpinnerMasterCommand
import mu.KotlinLogging

object Spinner : SubsystemBase() {
    private val spinnerTalon = WPI_TalonFX(Constants.Spinner.ID_SPINNER).apply {
        selectProfileSlot(0, 0)

        config_kP(0, 0.5)
        config_kI(0, 0.0)
        config_kD(0, 0.0)

        configMotionCruiseVelocity(20000)
        configMotionAcceleration(10000)
        configMotionSCurveStrength(1)

        configAllowableClosedloopError(0, 15)

        setSelectedSensorPosition(0)
    }

    private var spinnerVelocity: Int
        set(value) {
            spinnerTalon.configMotionCruiseVelocity(value)
        }

        get() {
            return spinnerTalon.selectedSensorVelocity
        }

    private val logger = KotlinLogging.logger("Spinner")

    init {
//        defaultCommand = SpinnerMasterCommand(this)
    }

    fun set_spinner(control_mode: TalonFXControlMode, value: Double) {
        spinnerTalon.set(control_mode, value)
    }
}
