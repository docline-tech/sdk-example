# Docline SDK Examples
![](https://drive.google.com/uc?export=view&id=1_VGN5i9_djalUq5SLYMeOCKvZgXteNSI)
## iOS
### Install dependecies

`$ pod install` 

Open the Docline workspace file with Xcode

### Update path and code
Update serverURL and code params, for some that are valid.

```
ViewController.swift

let setupData = Docline.Setup (serverURL: "serverURL")
let options = Docline.Options(roomCode: "roomCode")
Docline.join(setupData, options: options, delegate: self)
```

## Cordova
We can make a bash script for simplify this process, the -p flag is the platform (ios  or android).
Execute from the project's root folder.

`$ sh build_script.sh -p ios`

## Ionic - Cordova
We can make a bash script for simplify this process, the -p flag is the platform (ios  or android).
Execute from the project's root folder.

`$ sh build_script.sh -p ios`

## Ionic - Capacitor
We can make a bash script for simplify this process, the -p flag is the platform (ios  or android).
Execute from the project's root folder.

`$ sh build_script.sh -p ios`
