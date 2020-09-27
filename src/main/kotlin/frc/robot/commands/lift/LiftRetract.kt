package frc.robot.commands.lift

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.subsystems.Lift

// TODO: Rework

class LiftRetract : CommandBase() { // Retract the lift at whatever velocity is right
    init {
        addRequirements(Lift)
    }

    override fun execute() {
        Lift.retractControl(ControlMode.DutyCycle)
//        Lift.retractControl(ControlMode.AssistedMotion, PositionConfig(200))
    }

    override fun end(interrupted: Boolean) {
        Lift.retractControl(ControlMode.Disabled)
    }
}
