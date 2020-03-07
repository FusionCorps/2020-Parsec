package frc.robot.subsystems

import edu.wpi.cscore.HttpCamera
import edu.wpi.cscore.MjpegServer
import edu.wpi.cscore.VideoSource
import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Sendable
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Cameras : SubsystemBase(), Sendable {
    val liftCamera = CameraServer.getInstance().startAutomaticCapture("lift", 0)
    val intakeCamera = CameraServer.getInstance().startAutomaticCapture("intake", 1)
    val limelight = CameraServer.getInstance().startAutomaticCapture(HttpCamera("limelight",
            "http://10.66.72.11:5800/video/stream.mjpg"))

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

    var driverMode = true

    lateinit var switcher: MjpegServer

    init {
        SmartDashboard.putBoolean("Driver Mode", false)
        switcher = CameraServer.getInstance().addSwitchedCamera("switcher")
        switcher.source = intakeCamera
    }

    var switcherSource: VideoSource
        get() {
            return switcher.source
        }
        set(value) {
            switcher.source = value
        }

    override fun periodic() {
        if (driverMode) {
            if (limelightTable.getEntry("camMode").getDouble(0.0) != 1.0) {
                limelightTable.getEntry("camMode").setDouble(1.0)
            }
            if (limelightTable.getEntry("ledMode").getDouble(0.0) != 1.0) {
                limelightTable.getEntry("ledMode").setDouble(1.0)
            }
        } else {
            if (limelightTable.getEntry("camMode").getDouble(0.0) != 0.0) {
                limelightTable.getEntry("camMode").setDouble(0.0)
            }
            if (limelightTable.getEntry("ledMode").getDouble(0.0) != 0.0) {
                limelightTable.getEntry("ledMode").setDouble(3.0)
            }
        }
    }

    override fun initSendable(builder: SendableBuilder?) {
        builder!!.setSmartDashboardType("RobotPreferences")

        builder.addBooleanProperty("Driver Mode", { driverMode }, { x: Boolean -> driverMode = x })
    }
}
