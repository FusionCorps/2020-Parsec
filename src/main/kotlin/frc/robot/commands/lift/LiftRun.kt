package frc.robot.commands.lift

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.Lift

class LiftRun(lift: Lift) : SequentialCommandGroup() {
    private val mLift = Lift

    init {
        addRequirements(Lift)
        // addCommands(whatever goes here)
    }
}

// By saturday:
// Lift has to work:
//     -Extend, Pull
//     -Retract motor is a SPARK MAX -> Research the PID Controllers from WPILib
// INDEXER - Dump comand revisions
// Custom talon class - move all motors
// Camera streams - Lifecam + Limelight