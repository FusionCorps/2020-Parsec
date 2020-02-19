package frc.robot.commands.indexer

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.subsystems.Indexer

class IndexerMove(indexer: Indexer, direction: IndexerMovementDirection, times: Int = 1) : CommandBase() {
    private val mIndexer = indexer

    val mDirection = direction
    val mTimes = times

    var mTargetIndexerPosition: Double = 0.0
    val errorThreshold: Double = 4096.0

    init {
        addRequirements(mIndexer)
    }

    override fun initialize() {
        mTargetIndexerPosition = mIndexer.getCurrentPosition() +
            (
                mTimes * (
                    if (mDirection == IndexerMovementDirection.Forward) Constants.Indexer.SHIFT_TICKS
                    else -Constants.Indexer.SHIFT_TICKS
                    )
                )

        mIndexer.setBelt(TalonFXControlMode.MotionMagic, mTargetIndexerPosition)
    }

    override fun isFinished(): Boolean {
        val currentPosition = mIndexer.getCurrentPosition()

        return currentPosition > (mTargetIndexerPosition - errorThreshold)
    }
}

enum class IndexerMovementDirection {
    Forward,
    Backward
}
