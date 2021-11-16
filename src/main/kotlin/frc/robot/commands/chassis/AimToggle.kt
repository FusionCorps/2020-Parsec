package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.fusion.motion.InputRecording.isRecording
import frc.robot.subsystems.Chassis

class AimToggle: InstantCommand() {

    init {
        addRequirements(Chassis)
    }

    override fun initialize() {
        Chassis.aiming = !Chassis.aiming
    }



}