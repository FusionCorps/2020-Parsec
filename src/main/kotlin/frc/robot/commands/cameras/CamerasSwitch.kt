package frc.robot.commands.cameras

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.subsystems.CameraOptions
import frc.robot.subsystems.Cameras

class CamerasSwitch : InstantCommand() {
    init {
        addRequirements(Cameras)
    }

    override fun initialize() {
        when (Cameras.source) {
            CameraOptions.Intake -> Cameras.source = CameraOptions.Rear
            CameraOptions.Rear -> Cameras.source = CameraOptions.Limelight
            CameraOptions.Limelight -> Cameras.source = CameraOptions.Intake
        }
    }
}
