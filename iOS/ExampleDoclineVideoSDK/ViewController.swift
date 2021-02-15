//
//  ViewController.swift
//  ExampleDoclineVideoSDK
//
//  Created by Antonio Romero Gómez on 03/09/2020.
//  Copyright © 2020 Aplicaciones Salud SL. All rights reserved.
//

import UIKit
import DoclineVideoSDK

class ViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    @IBAction func joinButtonAction(_ sender: Any) {
        let setupData = Docline.Setup (serverURL: "serverURL")
        let options = Docline.Options(roomCode: "roomCode")
        Docline.join(setupData, options: options, delegate: self)
    }
}

extension ViewController: DoclineDelegate {
    
    func consultationJoinSuccess(_ sender: DoclineViewController) {
        NSLog("Video consultation joined successfully")
        present(sender, animated: true, completion: nil)
    }
    
    func consultationJoinError(_ error: Docline.ResponseError) {
        NSLog("Video consultation failed to join")
    }
    
    func consultationTerminated(_ screenView: Docline.ScreenView) {
        NSLog("Video consultation finished")
        dismiss(animated: true, completion: nil)
    }
}

