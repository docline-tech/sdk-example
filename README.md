# Examples Docline SDK

## Android - Steps to run (Coming soon)

## iOS - Steps to run
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

## Cordova - Steps to run (Coming soon)

## Ionic - Steps to run (Coming soon)

