package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.fusion.motion.ControlMode
import frc.robot.subsystems.Shooter

class ShooterCoastDown : InstantCommand() {
    init {
        addRequirements(Shooter)
    }

    override fun initialize() {
//        Shooter.control(ControlMode.DutyCycle, DutyCycleConfig(0.0))
        Shooter.control(ControlMode.Disabled)
    }
}
