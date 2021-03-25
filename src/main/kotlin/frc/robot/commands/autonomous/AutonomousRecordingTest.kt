package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.fusion.motion.InputRecording
import frc.robot.subsystems.Chassis
import mu.KotlinLogging

class AutonomousRecordingTest: InstantCommand() {

    init {
        addRequirements(Chassis)
    }

    override fun initialize() {
        for (row in InputRecording.inputs) {
            KotlinLogging.logger("Testing Auton").info { row.toString() }
        }
        KotlinLogging.logger("Bruh Moment").info{ "Bruh" }
    }

}