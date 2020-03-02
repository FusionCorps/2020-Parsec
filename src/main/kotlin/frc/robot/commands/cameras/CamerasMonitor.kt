package frc.robot.commands.cameras

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Cameras
import frc.robot.subsystems.Chassis

class CamerasMonitor : CommandBase() {
    init {
        addRequirements(Cameras)
    }

    override fun execute() {
    }
}
