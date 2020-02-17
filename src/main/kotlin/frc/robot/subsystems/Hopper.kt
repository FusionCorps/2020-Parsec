package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.hopper.HopperRun

object Hopper : SubsystemBase() {
    private val victorSPXBelt = WPI_VictorSPX(Constants.Hopper.ID_VICTORSPX).apply {
        setInverted(InvertType.InvertMotorOutput)

        config_kP(0, 0.5)
        config_kI(0, 0.0)
        config_kD(0, 0.0)
    }

    var currentHopperVelocity = 0.0

    init {
        setDefaultCommand(HopperRun(this))
    }

    fun setBelt(controlMode: VictorSPXControlMode = VictorSPXControlMode.Velocity, value: Double) {
        victorSPXBelt.set(controlMode, value)
    }
}
