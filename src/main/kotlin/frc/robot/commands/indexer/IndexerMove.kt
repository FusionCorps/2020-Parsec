package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.PositionConfig
import frc.robot.subsystems.Indexer

class IndexerMove(direction: IndexerMovementDirection, times: Int = 1) : CommandBase() {
    val mDirection = direction
    val mTimes = times

    var mTargetIndexerPosition: Double = 0.0
    val errorThreshold: Double = 4096.0

    init {
        addRequirements(Indexer)
    }

    override fun initialize() {
        if (Indexer.isBallTop) {
            end(true)
        }

        mTargetIndexerPosition = Indexer.beltPosition +
            (
                mTimes * (
                    if (mDirection == IndexerMovementDirection.Forward) Constants.Indexer.SHIFT_TICKS
                    else -Constants.Indexer.SHIFT_TICKS
                    )
                )

        Indexer.control(ControlMode.Position, PositionConfig(mTargetIndexerPosition.toInt()))
    }

    override fun isFinished(): Boolean {
        val currentPosition = Indexer.beltPosition

        return currentPosition > (mTargetIndexerPosition - errorThreshold)
    }

    override fun end(interrupted: Boolean) {
        if (interrupted) {
            Indexer.control(ControlMode.Disabled)
        }
    }
}

enum class IndexerMovementDirection {
    Forward,
    Backward
}
