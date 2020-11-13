package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis

// Note Deprecated

class ChassisBrake : CommandBase() {
    init {
        addRequirements(Chassis)
    }

    override fun execute() {
        Chassis.tankDrive(0.0, 0.0)
    }
}
