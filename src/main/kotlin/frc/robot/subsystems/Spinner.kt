package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.spinner.SpinnerMasterCommand
import mu.KotlinLogging

object Spinner : SubsystemBase() {
    private val SpinnerTalon = WPI_TalonFX(Constants.Spinner.ID_SPINNER).apply {
        config_kP(0, 0.5)
        config_kI(0, 0.0)
        config_kD(0, 0.0)

        configMotionCruiseVelocity(20000)
        configMotionAcceleration(10000)
        configMotionSCurveStrength(1)

        setSelectedSensorPosition(0)
    }

    private val logger = KotlinLogging.logger("Spinner")

    init {
        defaultCommand = SpinnerMasterCommand(this)
    }

    fun set_spinner(control_mode: TalonFXControlMode, value: Double) {
        SpinnerTalon.set(control_mode, value)
    }

//    fun get_spinner(): Pair {
//        SpinnerTalon.get() to SpinnerTalon.getSelectedSensorVelocity()
//        return SpinnerTalon.get() to SpinnerTalon.getSelectedSensorVelocity()
//    }
}
