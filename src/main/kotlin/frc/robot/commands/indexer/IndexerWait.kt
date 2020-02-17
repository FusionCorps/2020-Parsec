package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Indexer

class IndexerWait(indexer: Indexer) : CommandBase() {
    private val mIndexer = indexer

    init {
        addRequirements(mIndexer)
    }

    override fun isFinished(): Boolean {
        return (mIndexer.isBallFront()) && !(mIndexer.isBallTop())
    }

    override fun end(interrupted: Boolean) {
        if (mIndexer.isBallFront() && !interrupted) {
            IndexerMove(mIndexer, IndexerMovementDirection.Forward).schedule()
        }
    }
}
