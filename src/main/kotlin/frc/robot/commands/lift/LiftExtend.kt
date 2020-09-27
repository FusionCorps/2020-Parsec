package frc.robot.commands.lift

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.subsystems.Lift

// TODO: Rework

class LiftExtend : CommandBase() { // Run the extension motor at 10%
    init {
        addRequirements(Lift)
    }

    override fun initialize() {
        Lift.extendControl(ControlMode.DutyCycle, DutyCycleConfig(0.4))
//        Lift.extendControl(ControlMode.AssistedMotion)
    }

    override fun end(interrupted: Boolean) {
        Lift.extendControl(ControlMode.Disabled)
    }
}
