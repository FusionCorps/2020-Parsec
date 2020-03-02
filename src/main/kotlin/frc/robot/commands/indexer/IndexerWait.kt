package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Indexer

class IndexerWait : CommandBase() {
    init {
        addRequirements(Indexer)
    }

    override fun isFinished(): Boolean {
        return (Indexer.isBallFront) && !(Indexer.isBallTop)
    }
}
