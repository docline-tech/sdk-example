/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready
document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
    // Cordova is now initialized. Have fun!
    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');
}

document.getElementById("join").addEventListener("click", join);

function join() {
    let code = document.getElementById("code").value;
    let eventId = docline.EventId.consultationJoinSuccess;
    let eventId2 = docline.EventId.updatedCameraStatus;
    
    docline.addEventListener(eventId, consultationJoinSuccess);
    docline.addEventListener(eventId2, updatedCameraStatus);
    docline.setHandleError(handleError);
    let apiURL = "https://api-url";
    docline.join(code, apiURL);
}

function handleError(error) {
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

function consultationJoinSuccess() {
    console.log(`consultationJoinSuccess`);
}

function updatedCameraStatus(data) {
    console.log(`updatedCameraState: { eventId: ${data.eventId}, screenId: ${data.screenId}, isEnabled: ${data.isEnabled} }`);
}