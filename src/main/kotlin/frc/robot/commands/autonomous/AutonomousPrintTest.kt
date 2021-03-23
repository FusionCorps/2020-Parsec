package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.InstantCommand
import mu.KotlinLogging

class AutonomousPrintTest(output: String) : InstantCommand() {

    var mOutput = output

    init {

    }

    override fun execute() {
        KotlinLogging.logger("test").info {mOutput}
    }

}