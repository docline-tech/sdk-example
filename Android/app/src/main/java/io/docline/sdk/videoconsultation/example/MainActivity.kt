package io.docline.sdk.videoconsultation.example

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import docline.doclinevideosdk.DoclineVideocallView
import docline.doclinevideosdk.core.listeners.ArchiveListener
import docline.doclinevideosdk.core.listeners.ChatListener
import docline.doclinevideosdk.core.listeners.ConnectionListener
import docline.doclinevideosdk.core.listeners.DoclineListener
import docline.doclinevideosdk.core.listeners.enums.CameraSource
import docline.doclinevideosdk.core.listeners.enums.ResponseError
import docline.doclinevideosdk.core.listeners.enums.ScreenView
import docline.doclinevideosdk.views.DoclineActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runWithActivity()
        //runWithSDK()
    }

    private fun runWithActivity() {
        val filter = IntentFilter()
        filter.addAction(DoclineActivity.GENERAL_LISTENER)
        filter.addAction(DoclineActivity.CONNECTION_LISTENER)
        filter.addAction(DoclineActivity.ARCHIVE_LISTENER)
        filter.addAction(DoclineActivity.CHAT_LISTENER)
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent?.action
                val error = intent?.getSerializableExtra(DoclineActivity.ERROR) as? ResponseError
                val method = intent?.extras?.getSerializable(DoclineActivity.METHOD) as? DoclineListener.MethodName
                Log.d(TAG, "Broadcast onReceive ( " +
                        "action = $action, " +
                        "error = ${error?.name}, " +
                        "method = ${method?.name}" +
                        " )")
            }
        }
        registerReceiver(receiver, filter)

        val intent = Intent(this, DoclineActivity::class.java)
        intent.putExtra(DoclineActivity.CODE, ROOM_CODE)
        intent.putExtra(DoclineActivity.URL, SERVER_URL)
        intent.putExtra(DoclineActivity.ENABLE_SETTINGS, ENABLE_SETTINGS_SCREEN)
        startActivity(intent)
    }

    private fun runWithSDK() {
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
        options["enableSetupScreen"] = ENABLE_SETTINGS_SCREEN

        // connectarmos con la videollamada y los parámetros
        doclineVideoCall.join(options)
    }

    private val doclineListener = object: DoclineListener {
        override fun consultationJoinError(error: ResponseError) {
            Log.d(TAG, "consultationJoinError( error = ${error.name} )")
        }

        override fun consultationTerminated(screenView: ScreenView) {
            Log.d(TAG, "consultationTerminated( screenView = ${screenView.name} )")
        }

        override fun updatedCamera(screenView: ScreenView, source: CameraSource) {
            Log.d(TAG, "updatedCamera( screenView = ${screenView.name}, source = ${source.name} )")
        }

        override fun updatedCamera(screenView: ScreenView, isEnabled: Boolean) {
            Log.d(TAG, "updatedCamera( screenView = ${screenView.name}, isEnabled = $isEnabled )")
        }

        override fun updatedMicrophone(screenView: ScreenView, isEnabled: Boolean) {
            Log.d(TAG, "updatedMicrophone( screenView = ${screenView.name}, isEnabled = $isEnabled )")
        }

        override fun show(screenView: ScreenView) {
            Log.d(TAG, "show( screenView = ${screenView.name} )")
        }

        override fun consultationJoined() {
            Log.d(TAG, "consultationJoined()")
        }
    }

    private val doclineConnectionListener = object: ConnectionListener {
        override fun consultationReconnecting() {
            Log.d(TAG, "consultationReconnecting()")
        }

        override fun consultationReconnected() {
            Log.d(TAG, "consultationReconnected()")
        }

        override fun disconnectedByError() {
            Log.d(TAG, "disconnectedByError()")
        }

        override fun userSelectExit() {
            Log.d(TAG, "userSelectExit")
        }

        override fun userTryReconnect() {
            Log.d(TAG, "userTryReconnect()")
        }
    }

    private val doclineArchiveListener = object: ArchiveListener {
        override fun screenRecordingStarted() {
            Log.d(TAG, "screenRecordingStarted()")
        }

        override fun screenRecordingFinished() {
            Log.d(TAG, "screenRecordingFinished()")
        }

        override fun screenRecordingApproved() {
            Log.d(TAG, "screenRecordingApproved()")
        }

        override fun screenRecordingDenied() {
            Log.d(TAG, "screenRecordingDenied()")
        }
    }

    private val doclineChatListener = object: ChatListener {
        override fun messageSent() {
            Log.d(TAG, "messageSent()")
        }

        override fun messageReceived() {
            Log.d(TAG, "messageReceived()")
        }
    }


    companion object {
        const val TAG = "sdk-video-example"

        const val SERVER_URL = "your-api-url-here"
        const val ENABLE_SETTINGS_SCREEN = true
        const val ROOM_CODE = "your-code-here"
    }
}