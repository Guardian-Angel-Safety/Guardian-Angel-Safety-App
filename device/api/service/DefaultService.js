'use strict';


var TEMP_RISE_TIME = 1000 * 60; //1000ms = 1 second * 60 seconds in a minute = 60,000ms 

/**
 * Returns GAS Status Data
 *
 * returns GASResponseData
 **/

function lerp(v0, v1, t) {
    return v0*(1-t)+v1*t
};
function clamp(num, min, max) {
  return num <= min ? min : num >= max ? max : num;
}
function fluctuate(value, min, max, precision, dt)
{
    return clamp(value + (Math.sin(dt) * precision), min, max);
}

class GASSimulation
{
    constructor()
    {
        this.setDemoState(DEMOSTATE.DEMO_IDLE);
        this.currentTemperature = 80.0;
        this.currentPressure = 7.7;
        this.currentBatteryLevel = 100;
        this.initialBatteryLevel = this.currentBatteryLevel;
        this.initialTemperature = this.currentTemperature;
        this.initialPressure = this.currentPressure;
        this.timerTicks = 0;
        this.lastUpdate = -1.0;
        this.shouldBroadcast = true;
    }

    updateSimulation()
    {
        if(this.lastUpdate == -1.0)
        {
            this.lastUpdate = Date.now();
            return;
        }
        var dt = Date.now() - this.lastUpdate;
        var fluctuatedTemperature = fluctuate(this.currentTemperature, 78.0, 100.0, 0.1, dt);
        var fluctuatedPressure = fluctuate(this.currentPressure, 37.0, 37.2, 0.1, dt); 
        this.currentTemperature = fluctuatedTemperature;
        this.currentPressure = fluctuatedPressure;
        switch(this.demoState)
        {
            case DEMOSTATE.DEMO_LOWBATTERY:
            {
                this.currentBatteryLevel = 50;
                break;
            }
            case DEMOSTATE.DEMO_TEMPRAISE:
            {
                this.tempRiseStartTime = Date.now();
                this.setDemoState(DEMOSTATE.DEMO_TEMPRISING);
                break;
            }
            case DEMOSTATE.DEMO_TEMPRISING:
            {
                var progression = clamp((Date.now() - this.tempRiseStartTime) / TEMP_RISE_TIME, 0, 1);
                this.currentTemperature = lerp(80, 100, progression);
                if(this.currentTemperature >= 100)
                {
                    this.setDemoState(DEMOSTATE.DEMO_TEMPHOT);
                }
            }
            case DEMOSTATE.DEMO_TEMPHOT:
            {

                break;
            }
            case DEMOSTATE.DEMO_DISCONNECT:
            {
                this.shouldBroadcast = false;
                break;
            }
            case DEMOSTATE.DEMO_RECONCILE:
            {
                this.shouldBroadcast = true;
                break;
            }
        }
        this.lastUpdate = Date.now();
    }

    provideJSON()
    {
        var JSONSchema = 
        {
            pressure_data: this.currentPressure,
            temperature_data: this.currentTemperature,
            battery_level: this.currentBatteryLevel
        };
        return JSONSchema;
    }

    setDemoState(demoState)
    {
        if(demoState == DEMOSTATE.DEMO_IDLE && this.demoState != DEMOSTATE.DEMO_IDLE)
        {
            this.currentPressure = this.initialPressure;
            this.currentTemperature = this.initialTemperature;
        }
        if(demoState == DEMOSTATE.DEMO_TEMPHOT && this.demoState == DEMOSTATE.DEMO_TEMPRISING)
        {
            console.log("HOT!");
        }
        console.log("Device demo state has been set to: " + demoState);
        this.demoState = demoState;
    }
}
var Simulation = new GASSimulation();
exports.rootGET = function() {
  return new Promise(function(resolve, reject) {
      Simulation.updateSimulation();
      if(Simulation.shouldBroadcast)
      {
        resolve(Simulation.provideJSON());
      }
      else 
      {
          reject();
      }
  });
}

/**
 * Sets demo state.
 *
 * user GASControlMessage The user to create. (optional)
 * no response value expected for this operation
 **/
exports.rootPOST = function(results) {
  return new Promise(function(resolve, reject) {
    var postUpdate = results.message.value;
    Simulation.setDemoState(postUpdate.demo_state);
    resolve();
  });
}



