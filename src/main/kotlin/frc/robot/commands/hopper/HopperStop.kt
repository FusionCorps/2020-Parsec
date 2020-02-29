package frc.robot.commands.hopper

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.subsystems.Hopper

class HopperStop : CommandBase() {
    init {
        addRequirements(Hopper)
    }

    override fun initialize() {
        Hopper.control(ControlMode.Disabled)
    }
}
