package frc.robot.commands.lift

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.PositionConfig
import frc.robot.subsystems.Lift

class LiftRetract : CommandBase() { // Retract the lift at whatever velocity is right
    init {
        addRequirements(Lift)
    }

    override fun execute() {
        Lift.retractControl(ControlMode.AssistedMotion, PositionConfig(200))
//        Lift.setRetractPID(200.0, ControlType.kVelocity)
    }

    override fun end(interrupted: Boolean) {
        Lift.retractControl(ControlMode.Disabled)
//        Lift.setRetractPID(0.0, ControlType.kVelocity)
    }
}
