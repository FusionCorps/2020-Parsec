package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.subsystems.Chassis
import frc.robot.subsystems.Indexer

class IndexerToggle: InstantCommand() {
    init {
        addRequirements(Indexer)
    }

    override fun initialize() {
        Indexer.isAutomating = !Indexer.isAutomating
    }
}