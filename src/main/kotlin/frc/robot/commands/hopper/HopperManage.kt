package frc.robot.commands.hopper

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.Hopper
import frc.robot.subsystems.Indexer

// Hopper command manager

class HopperManage : SequentialCommandGroup() {
    private val hopperIntake = HopperRunAt(value = 0.4).withTimeout(0.5)
    private val hopperReverse = HopperRunAt(value = -0.4).withTimeout(0.25)

    private class HopperWait : CommandBase() {
        init {
            addRequirements(Hopper)
        }

        override fun isFinished(): Boolean {
            return Indexer.isBallFront && !Indexer.isBallTop
        }
    }

    init {
//        addCommands(HopperWait(), hopperIntake, hopperReverse)
        addCommands(HopperWait())
    }
}
