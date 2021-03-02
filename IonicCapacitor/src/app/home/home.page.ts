import { Component } from '@angular/core';
import { Plugins } from '@capacitor/core';
const { DoclineSDK } = Plugins;
import { EventId, ErrorType } from 'capacitor-plugin-docline-sdk';

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

    let apiURL = "https://api-url";
    
    DoclineSDK.join({
      code: this.code,
      path: apiURL
    }).catch(this.handleError);
  }


  /**
   * DoclineSDK Logic
   */

  configureEventAndError() {
    let eventId = EventId.consultationJoinSuccess;    
    let eventId2 = EventId.updatedCameraStatus;
    let eventId3 = EventId.consultationTerminated;

    DoclineSDK.addListener( eventId , this.consultationJoinSuccess);
    DoclineSDK.addListener( eventId2 , this.updatedCameraStatus);
    DoclineSDK.addListener( eventId3 , this.consultationTerminated);
  }

  handleError(error) {
    
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

  consultationJoinSuccess(info: any) {
    console.log(`consultationJoinSuccess: ${JSON.stringify(info)}`);
  }

  updatedCameraStatus(data) {
    console.log(`updatedCameraState: { eventId: ${data.eventId}, screenId: ${data.screenId}, isEnabled: ${data.isEnabled} }`);
  }


  consultationTerminated(info: any) {
    console.log(`consultationTerminated: ${JSON.stringify(info)}`);
  }
}
