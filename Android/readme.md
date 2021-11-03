# Ejemplo de implementación del SDK Videoconsulta de Docline

## 1. Configuraciones del gradle
Agregar las siguientes líneas al fichero settings.gradle (raíz del proyecto):
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url 'https://nexus.docline.com/repository/maven-public/' }
        maven { url 'https://tokbox.bintray.com/maven' }
    }
}
```

Agregar las siguientes líneas en el fichero build.gradle (raíz de la app de tu proyecto):
```
plugins {
    ...
    id 'kotlin-kapt'
}
android {
    ...
    dataBinding {
        enabled = true
    }
    viewBinding {
        enabled = true
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
dependencies {
    ...
    implementation 'docline.io:DoclineSDK:1.0.3'
}
```

## 2. Configuración archivo colors.xml
Agregar el color con el nombre **colorPrimaryDark** al fichero colors.xml:
```
<color name="colorPrimaryDark">#44FE98</color>
```

## 3. Configuración archivo Manifest.xml
Deshabilitamos el cambio de orientación en el activity que contenga el componente DoclineVideocallView
```
<activity 
    ...
    android:screenOrientation="portrait"
    />
```

## 4.  Uso del componente DoclineVideoCall 
Agregamos el componente DoclineVideocallView al layout donde queramos utilizarlo:
```
<LinearLayout>

    <docline.doclinevideosdk.DoclineVideocallView
        android:id="@+id/doclineView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

Configuramos el componente en el activity que queramos utilizarlo:
```
override fun onCreate(savedInstanceState: Bundle?) {
    ...

    // find component in layout to use it
    val doclineVideoCall = findViewById(...)
    doclineVideoCall.init(this)
    
    val options = HashMap<String,Any>()
    // code to join to the video consultation
    options["roomCode"] = ROOM_CODE
    // server URL to join to the video consultation
    options["serverURL"] = SERVER_URL
    // [optional] show the setup screen before to join to the video consultation
    options["enableSetupScreen"] = true

    // join to the video consultation
    doclineVideoCall.join(options)

}
```

Opcionalmente, podemos definir diferentes listeners para escuchar eventos que sucedan:

```
doclineVideoCall.generalListener = object: DoclineListener {
    // called when error trying connecting to consultation
    override fun consultationJoinError(error: ResponseError) {}

    // called when consultation joined successfully
    override fun consultationJoined() {}

    // called when consultation finished successfully
    override fun consultationTerminated(screenView: ScreenView) {}

    // called when shows a new screen
    override fun show(screenView: ScreenView) {}

    // called when user switch front / back camera
    override fun updatedCamera(screenView: ScreenView, source: CameraSource) {}

    // called when user enabled or disabled camera
    override fun updatedCamera(screenView: ScreenView, isEnabled: Boolean) {}

    // called when user enabled or disabled microphone
    override fun updatedMicrophone(screenView: ScreenView, isEnabled: Boolean) {}
}

doclineVideoCall.connectionListener = object: ConnectionListener {
    // called when user reconnect to the videocall
    override fun consultationReconnected() {}

    // called when user is reconnecting to the videocall
    override fun consultationReconnecting() {}

    // called when videoconsultation has desconected by error
    override fun disconnectedByError() {}

    // called when user has pressed exit button
    override fun userSelectExit() {}

    // called when user is trying to reconnect
    override fun userTryReconnect() {}
}

doclineVideoCall.archiveListener = object: ArchiveListener {
    // called when has been aproved the screen recording
    override fun screenRecordingApproved() {}

    // called when has been denied the screen recording
    override fun screenRecordingDenied() {}

    // called when the screen recording has been finished
    override fun screenRecordingFinished() {}

    // called when the screen recording has started
    override fun screenRecordingStarted() {}
}

doclineVideoCall.chatListener = object: ChatListener {
    // called when user has received a chat message
    override fun messageReceived() {}

    // called when user has sent a chat message
    override fun messageSent() {}
}
```