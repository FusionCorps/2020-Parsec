package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.Indexer

class IndexerManage(indexer: Indexer) : SequentialCommandGroup() {
    private val mIndexer = indexer

    init {
        addCommands(IndexerWait(mIndexer), IndexerMove(mIndexer, IndexerMovementDirection.Forward))
    }
}
