package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis

class ChassisDriveAmt(xSpd: Double, zSpd: Double, timeout: Double) : CommandBase() {
    val mXSpd = xSpd
    val mZSpd = zSpd

    val mTimeout = timeout
    val timer = Timer()

    init {
        addRequirements(Chassis)
    }

    override fun initialize() {
        timer.reset()
        timer.start()
    }

    override fun execute() {
        Chassis.tankDrive(mXSpd, -mXSpd)
    }

    override fun isFinished(): Boolean {
        return timer.hasPeriodPassed(mTimeout)
    }

    override fun end(interrupted: Boolean) {
        Chassis.tankDrive(0.0, 0.0)
    }
}
