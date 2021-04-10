package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.fusion.motion.ControlMode
import frc.robot.subsystems.Chassis
import frc.robot.subsystems.Indexer

class IndexerIdle: InstantCommand() {

    init {
        addRequirements(Indexer)
    }

    override fun execute() {
        Indexer.control(ControlMode.Disabled)
    }

}