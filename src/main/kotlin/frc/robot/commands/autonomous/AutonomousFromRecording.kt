package frc.robot.commands.autonomous

import com.opencsv.CSVReader
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.chassis.ChassisReplay
import frc.robot.commands.chassis.ChassisReplayv2
import frc.robot.fusion.motion.InputRecording
import frc.robot.subsystems.Chassis
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter


class AutonomousFromRecording : SequentialCommandGroup() {



    init {
        addCommands(ChassisReplayv2())
    }
}