package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.Indexer

class IndexerDump(indexer: Indexer) : SequentialCommandGroup() {
    private val mIndexer = indexer

    init {
        addCommands(IndexerMove(mIndexer, IndexerMovementDirection.Forward, 6))
    }
}
