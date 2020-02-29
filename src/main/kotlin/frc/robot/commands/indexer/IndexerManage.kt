package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

class IndexerManage : SequentialCommandGroup() {
    init {
        addCommands(IndexerWait(), IndexerMove(IndexerMovementDirection.Forward))
    }
}
