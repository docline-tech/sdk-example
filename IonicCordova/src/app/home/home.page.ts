import { Component } from '@angular/core';
import docline from 'cordova-plugin-docline-sdk';

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
    let apiURL: string = "https://api-url";
    docline.join(this.code, apiURL);
  }

  /**
   * Docline Logic
   */

  configureEventAndError() {
    let eventId = docline.EventId.consultationJoinSuccess;
    let eventId2 = docline.EventId.updatedCameraStatus;
    
    docline.addEventListener(eventId, this.consultationJoinSuccess);
    docline.addEventListener(eventId2, this.updatedCameraStatus);
    docline.setHandleError(this.handleError);
  }

  handleError(error) {
    let types = docline.ErrorType;

    switch (error.type) {
      case types.unauthorizedError:
        console.log("unauthorizedError");
        alert("unauthorizedError");
        break;
      case types.emptyCodeError:
        console.log("emptyCodeError");
        alert("emptyCodeError");
        break;
      case types.connectionError:
        console.log("connectionError");
        alert("connectionError");
        break;
      case types.customError:
        console.log(`customError(${error.message})`);
        alert("customError");
        break;
      case types.defaultError:
        console.log("defaultError");
        alert("defaultError");
        break;
      default: 
        break;
    }    
  }

  consultationJoinSuccess() {
    console.log(`consultationJoinSuccess`);
  }

  updatedCameraStatus(data) {
    console.log(`updatedCameraState: { eventId: ${data.eventId}, screenId: ${data.screenId}, isEnabled: ${data.isEnabled} }`);
  }
}
