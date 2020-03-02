package frc.robot.commands.hopper

import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.Hopper
import frc.robot.subsystems.Indexer

class HopperManage : SequentialCommandGroup() {
    private val hopperIntake = HopperRunAt(value = 0.4).apply {
        withTimeout(0.5)
    }
    private val hopperReverse = HopperRunAt(value = -0.4).apply {
        withTimeout(0.25)
    }

    private class HopperWait : CommandBase() {
        init {
            addRequirements(Hopper)
        }

        override fun isFinished(): Boolean {
            return Indexer.isBallFront
        }
    }

    init {
        addCommands(HopperWait(), hopperIntake, hopperReverse)
    }
}