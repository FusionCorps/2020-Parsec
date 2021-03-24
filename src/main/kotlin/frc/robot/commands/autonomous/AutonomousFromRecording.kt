package frc.robot.commands.autonomous

import com.opencsv.CSVReader
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter


class AutonomousFromRecording : SequentialCommandGroup() {
    var csvReader = CSVReader(FileReader("recording.csv"))
    private val recording: MutableList<Array<String>> = csvReader.readAll()


    init {
        for (row in recording) {
            try {
                addCommands(ChassisDriveAmt(row[0].toDouble(), row[1].toDouble(), 0.02))
            } catch (e: IndexOutOfBoundsException) {
                // do something here?
            }
        }
    }
}