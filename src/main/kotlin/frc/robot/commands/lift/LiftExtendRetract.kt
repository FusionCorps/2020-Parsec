package frc.robot.commands.lift

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.subsystems.Lift

// TODO: Rework

class LiftExtendRetract : CommandBase() {
    init {
        addRequirements(Lift)
    }

    override fun initialize() {
        Lift.extendControl(ControlMode.DutyCycle, DutyCycleConfig(-0.4))
    }

    override fun end(interrupted: Boolean) {
        Lift.extendControl(ControlMode.Disabled)
    }
}
