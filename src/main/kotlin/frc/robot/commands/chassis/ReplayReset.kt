package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.fusion.motion.InputRecording
import mu.KotlinLogging

class ReplayReset: InstantCommand() {

    override fun initialize() {
        KotlinLogging.logger("ReplayReset").info {"Resetting"}

        InputRecording.inputs = mutableListOf(listOf(0.0, 0.0))

    }

}