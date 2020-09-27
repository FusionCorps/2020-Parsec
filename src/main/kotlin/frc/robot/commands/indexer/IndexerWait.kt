package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Indexer
import mu.KotlinLogging

// Wait til ball at front and not at top.

class IndexerWait : CommandBase() {
    init {
        addRequirements(Indexer)
    }

    override fun isFinished(): Boolean {
        return (Indexer.isBallFront) && !(Indexer.isBallTop)
    }

    override fun end(interrupted: Boolean) {
        super.end(interrupted)
        KotlinLogging.logger("IndexerWait").info { "IndexerWait ended" }

        IndexerMove(IndexerMovementDirection.Forward, 1).schedule()
    }
}
