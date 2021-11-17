package io.docline.sdk.videoconsultation.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import docline.doclinevideosdk.DoclineVideocallView
import docline.doclinevideosdk.core.listeners.ArchiveListener
import docline.doclinevideosdk.core.listeners.ChatListener
import docline.doclinevideosdk.core.listeners.ConnectionListener
import docline.doclinevideosdk.core.listeners.DoclineListener
import docline.doclinevideosdk.core.listeners.enums.CameraSource
import docline.doclinevideosdk.core.listeners.enums.ResponseError
import docline.doclinevideosdk.core.listeners.enums.ScreenView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val doclineVideoCall = findViewById<DoclineVideocallView>(R.id.doclineVideoCallView)
        doclineVideoCall.init(this)
        doclineVideoCall.generalListener = doclineListener
        doclineVideoCall.connectionListener = doclineConnectionListener
        doclineVideoCall.archiveListener = doclineArchiveListener
        doclineVideoCall.chatListener = doclineChatListener


        val options = HashMap<String,Any>()
        // Código para unirse a la videollamada
        options["roomCode"] = ROOM_CODE
        // URL del servidor de la videollamada
        options["serverURL"] = SERVER_URL
        // [Opcional] para habilitar la setupScreen
        options["enableSetupScreen"] = SHOW_SETUP_SCREEN

        // connectarmos con la videollamada y los parámetros
        doclineVideoCall.join(options)
    }

    private val doclineListener = object: DoclineListener {
        override fun consultationJoinError(error: ResponseError) {
            Log.e(TAG, "consultationJoinError( ${error})")
        }

        override fun consultationJoined() {
            Log.i(TAG, "consultationJoined()")
        }

        override fun consultationTerminated(screenView: ScreenView) {
            Log.i(TAG, "consultationTerminated(${screenView})")
            finish()
        }

        override fun show(screenView: ScreenView) {
            Log.i(TAG, "show(${screenView})")
        }

        override fun updatedCamera(screenView: ScreenView, source: CameraSource) {
            Log.i(TAG, "updatedCamera(${screenView}, ${source})")
        }

        override fun updatedCamera(screenView: ScreenView, isEnabled: Boolean) {
            Log.i(TAG, "updatedCamera(${screenView}, ${isEnabled})")
        }

        override fun updatedMicrophone(screenView: ScreenView, isEnabled: Boolean) {
            Log.i(TAG, "updatedMicrophone(${screenView}, ${isEnabled})")
        }
    }

    private val doclineConnectionListener = object: ConnectionListener {
        override fun consultationReconnected() {
            Log.i(TAG, "consultationReconnected()")
        }

        override fun consultationReconnecting() {
            Log.i(TAG, "consultationReconnecting()")
        }

        override fun disconnectedByError() {
            Log.i(TAG, "disconnectedByError()")
        }

        override fun userSelectExit() {
            Log.i(TAG, "userSelectExit()")
        }

        override fun userTryReconnect() {
            Log.i(TAG, "userTryReconnect()")
        }
    }

    private val doclineArchiveListener = object: ArchiveListener {
        override fun screenRecordingApproved() {
            Log.i(TAG, "screenRecordingApproved()")
        }

        override fun screenRecordingDenied() {
            Log.i(TAG, "screenRecordingDenied()")
        }

        override fun screenRecordingFinished() {
            Log.i(TAG, "screenRecordingFinished()")
        }

        override fun screenRecordingStarted() {
            Log.i(TAG, "screenRecordingStarted()")
        }
    }

    private val doclineChatListener = object: ChatListener {
        override fun messageReceived() {
            Log.i(TAG, "messageReceived()")
        }

        override fun messageSent() {
            Log.i(TAG, "messageSent()")
        }
    }


    companion object {
        const val TAG = "sdk-video-example"

        const val SERVER_URL = "your-url-here"
        const val ROOM_CODE = "your-code-here"
        const val SHOW_SETUP_SCREEN = true
    }
}