package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter


class AutonomousFromRecording : SequentialCommandGroup() {
    var csvReader = BufferedReader(FileReader("test.csv"))
    var dataArray = mutableListOf(listOf("0.0", "0.0"))
    var lineCurrent = listOf("0.0, 0.0")
    val csvWriter = FileWriter("test.csv")
    



    init {



    }
}