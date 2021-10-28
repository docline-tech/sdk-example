import { Component } from '@angular/core';

import { DoclineSDKPlugin, ErrorData, ErrorType, EventData, EventId } from 'capacitor-plugin-docline-sdk';
import { DoclineSDK } from 'capacitor-plugin-docline-sdk';
const docline: DoclineSDKPlugin = DoclineSDK as DoclineSDKPlugin;

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  code: string = "";

  constructor() {}
  
  /**
   * join handle action
   */
  
  join() {
    this.configureEventAndError();

    let apiURL: string = "https://apivideo-b2b-dev.docline.eu/apivideo";
    let color = "#0a73ba"

    docline.join({
      code: this.code,
      path: apiURL,
      color: color
    });    
  }

  /**
   * DoclineSDK Logic
   */

  configureEventAndError() {
    docline.addListener(EventId.consultationJoinSuccess, this.consultationJoinSuccess);
    docline.addListener(EventId.updatedCameraStatus, this.updatedCameraStatus);
    docline.addListener(EventId.consultationTerminated, this.consultationTerminated);
    docline.addListener(EventId.error, this.handleError);
    docline.addListener(EventId.participantConnected, this.participantConnected);
  }

  handleError(error: ErrorData) {
    
    switch (error.type) {
      case ErrorType.unauthorizedError:
        console.log("unauthorizedError");
        alert("unauthorizedError");
        break;
      case ErrorType.emptyCodeError:
        console.log("emptyCodeError");
        alert("emptyCodeError");
        break;
      case ErrorType.connectionError:
        console.log("connectionError");
        alert("connectionError");
        break;
      case ErrorType.customError:
        console.log(`customError(${error.message})`);
        alert("customError");
        break;
      case ErrorType.defaultError:
        console.log("defaultError");
        alert("defaultError");
        break;
      default: 
        break;
    }    
  }

  consultationJoinSuccess(event: EventData) {
    console.log(`consultationJoinSuccess: ${JSON.stringify(event)}`);
  }

  updatedCameraStatus(event: EventData) {
    console.log(`updatedCameraState: { eventId: ${event.eventId}, screenId: ${event.screenId}, isEnabled: ${event.isEnabled} }`);
  }

  consultationTerminated(event: EventData) {
    console.log(`consultationTerminated: ${JSON.stringify(event)}`);
  }

  participantConnected(event: EventData) {
    console.log(`{eventId: ${event.eventId}, type: ${event.participantType}}`);
  }
}
