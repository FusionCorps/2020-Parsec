package frc.robot.subsystems

import edu.wpi.cscore.AxisCamera
import edu.wpi.cscore.UsbCamera
import edu.wpi.cscore.VideoSource
import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj2.command.SubsystemBase

enum class CameraOptions constructor(val cameraName: String, val camera: VideoSource) {
    Intake("intakeCamera", UsbCamera(Intake.cameraName, 0)),
    Rear("rearCamera", UsbCamera(Rear.cameraName, 0)),
    Limelight("limelight", AxisCamera(Limelight.cameraName, "http://limelight.local:5800"))
}

object Cameras : SubsystemBase() {
    private val switchedCamera = CameraServer.getInstance().addSwitchedCamera(CameraOptions.Limelight.cameraName)

    var source: CameraOptions = CameraOptions.Intake
        set(value) {
            switchedCamera.source = value.camera
            field = value
        }
        get() {
            return CameraOptions.valueOf(switchedCamera.source.name)
        }
}
