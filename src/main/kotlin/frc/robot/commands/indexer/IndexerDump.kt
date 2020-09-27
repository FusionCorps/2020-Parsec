package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

// Move all balls forward

class IndexerDump : SequentialCommandGroup() {
    init {
        addCommands(IndexerMove(IndexerMovementDirection.Forward, 6))
    }
}
