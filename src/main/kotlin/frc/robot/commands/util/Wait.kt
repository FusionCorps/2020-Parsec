package frc.robot.commands.util

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase

class Wait(vararg subsystems: SubsystemBase, period: Double) : CommandBase() {
    private val mPeriod = period

    init {
        addRequirements(*subsystems)
    }

    private val timer = Timer()

    override fun initialize() {
        timer.reset()
        timer.start()
    }

    override fun isFinished(): Boolean {
        return timer.hasPeriodPassed(mPeriod)
    }

    override fun end(interrupted: Boolean) {
        timer.stop()
    }
}
