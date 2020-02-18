package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Indexer

class IndexerRun(indexer: Indexer) : CommandBase() {
    private val m_indexer = indexer

    init {
        addRequirements(m_indexer)
    }

    override fun execute() {
    }
}
