package frc.robot.commands.lift

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Lift

class LiftRetract(lift: Lift) : CommandBase() { // Retract the lift at whatever velocity is right

    private val velocity = 20000.0
    private val mLift = lift

    init {
        addRequirements(mLift)
    }

    override fun execute() {
        mLift.setRetractVelocity(20000.0)
    }

    override fun cancel() {
        end(interrupted = true)
    }

    override fun end(interrupted: Boolean) {
        mLift.setRetractVelocity(0.0)
    }
}
