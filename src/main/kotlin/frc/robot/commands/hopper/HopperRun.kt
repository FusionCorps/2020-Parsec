package frc.robot.commands.hopper

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Hopper

class HopperRun(hopper: Hopper) : CommandBase() {
    private final val mHopper = hopper

    init {
        addRequirements(mHopper)
    }

    override fun execute() {
        mHopper.setBelt(value = mHopper.currentHopperVelocity)
    }
}
