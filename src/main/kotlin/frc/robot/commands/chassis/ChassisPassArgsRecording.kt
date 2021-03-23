package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.FileReader

// Note Deprecated

class ChassisPassArgsRecording() : CommandBase() {

    var row = 0

    var finishedCheck = false

    val csvReader = BufferedReader(FileReader("test.csv"))

    init {
        addRequirements(Chassis)
    }

    override fun initialize() {
    }

    override fun execute() {
        KotlinLogging.logger("Recording Execute").info { csvReader.readLine() }
    }


}