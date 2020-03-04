package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.PositionConfig
import frc.robot.subsystems.Indexer

class IndexerAutomate : CommandBase() {
    var mTargetIndexerPosition: Double = 0.0

    init {
        addRequirements(Indexer)
    }

    override fun execute() {
        if (Indexer.isBallTop) {
            Indexer.control(ControlMode.Disabled)
        } else if (Indexer.isBallFront) {
            Indexer.beltPosition = 0

            mTargetIndexerPosition = Indexer.beltPosition + Constants.Indexer.SHIFT_TICKS

            Indexer.control(ControlMode.Position, PositionConfig(mTargetIndexerPosition.toInt()))
        }
    }
}
