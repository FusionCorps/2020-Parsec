package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.FileReader




class AutonomousFromRecording {
    var csvReader = BufferedReader(FileReader("test.csv"))
    var dataArray = mutableListOf(listOf("0.0", "0.0"))
    var lineCurrent = listOf("0.0, 0.0")



    init {
//        TODO: Get this working
//        while (csvReader.readLine() != null) {
//            dataArray.add(csvReader.readLine().split(","))
//        }
//
//
//        for (row in dataArray) {
//            addCommands(ChassisDriveAmt(row[0].toDouble(), row[1].toDouble(), 0.02))
//        }
//
//        addCommands(AutonomousPrintTest(dataArray[0][0]))
//
    }
}