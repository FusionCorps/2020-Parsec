package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Climb

class ClimbRun(climb: Climb) : CommandBase() {
    private val mclimb = climb

    init {
        addRequirements(mclimb)
    }

    override fun execute() {
//        Ideal code:
//                Check if under bar and button is pressed
//                then lift -> close claw -> hoist.
//        Then let Malevolent down after button is pressed again
//        fin
    }

}
