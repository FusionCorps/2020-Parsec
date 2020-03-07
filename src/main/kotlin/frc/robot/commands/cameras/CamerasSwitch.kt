package frc.robot.commands.cameras

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.subsystems.Cameras

class CamerasSwitch : InstantCommand() {
    init {
        addRequirements(Cameras)
    }

    override fun initialize() {
        if (Cameras.switcher.source.name == "intake") {
            Cameras.setSwitcherSource(Cameras.liftCamera)
        } else {
            Cameras.setSwitcherSource(Cameras.intakeCamera)
        }
    }
}
