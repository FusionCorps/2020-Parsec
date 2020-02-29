package frc.robot.commands.lift

import com.revrobotics.ControlType
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Lift

class LiftRetract : CommandBase() { // Retract the lift at whatever velocity is right
    init {
        addRequirements(Lift)
    }

    override fun execute() {
        Lift.setRetractPID(200.0, ControlType.kVelocity)
    }

    override fun end(interrupted: Boolean) {
        Lift.setRetractPID(0.0, ControlType.kVelocity)
    }
}
