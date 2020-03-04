package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.subsystems.Indexer

class IndexerVelocity(dutyCycle: DutyCycleConfig) : CommandBase() {
    val mDutyCycle = dutyCycle

    init {
        addRequirements(Indexer)
    }

    override fun initialize() {
        Indexer.control(ControlMode.DutyCycle, mDutyCycle)
    }

    override fun end(interrupted: Boolean) {
        Indexer.control(ControlMode.Disabled)
    }
}
