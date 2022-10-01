package com.uc2control;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.api.ApiServiceCallback;
import com.api.ApiServiceCallbackAdapter;
import com.api.RestController;
import com.api.enums.LedModes;
import com.api.response.LedArrRequest;
import com.api.response.LedColorItem;
import com.api.response.LedSetRequest;

public class LedModel extends BaseObservable {
    private RestController restController;
    private String ledcount = "64";
    private String ledPin = "0";
    private boolean leds_turned_on = false;
    private int red = 255;
    private int green = 255;
    private int blue = 255;

    public LedModel(RestController restController)
    {
        this.restController = restController;
    }
    @Bindable
    public String getLedcount() {
        return ledcount;
    }

    public void setLedcount(String ledcount) {
        this.ledcount = ledcount;
        if (this.ledcount == ledcount)
            return;
        notifyPropertyChanged(BR.ledcount);
    }

    public void setLedsOn(boolean leds_turned_on) {
        if(this.leds_turned_on == leds_turned_on)
            return;
        this.leds_turned_on = leds_turned_on;
        notifyPropertyChanged(BR.ledsOn);
        sendLedEnableRequest();
    }

    @Bindable
    public boolean getLedsOn() {
        return leds_turned_on;
    }

    private void sendLedEnableRequest()
    {
        LedArrRequest request =new LedArrRequest();
        request.LEDArrMode = LedModes.full.ordinal();
        request.led_array =new LedColorItem[1];
        request.led_array[0] = new LedColorItem();
        request.led_array[0].id = 0;
        request.led_array[0].red = leds_turned_on ? red:0;
        request.led_array[0].green = leds_turned_on ? green:0;
        request.led_array[0].blue = leds_turned_on ? blue:0;
        restController.getRestClient().setLedArr(request,setLedCallback);
    }

    private void updateColors()
    {
        LedArrRequest request =new LedArrRequest();
        request.LEDArrMode = LedModes.full.ordinal();
        request.led_array =new LedColorItem[1];
        request.led_array[0] = new LedColorItem();
        request.led_array[0].id = 0;
        request.led_array[0].red = red;
        request.led_array[0].green = green;
        request.led_array[0].blue = blue;
        restController.getRestClient().setLedArr(request,setLedCallback);
    }

    private ApiServiceCallback<String> setLedCallback = new ApiServiceCallback<String>() {
        @Override
        public void onResponse(String response) {

        }
    };

    public void setRed(int red) {
        if (this.red == red)
            return;
        this.red = red;
        notifyPropertyChanged(BR.red);
        if (leds_turned_on)
            updateColors();
    }

    @Bindable
    public int getRed() {
        return red;
    }

    public void setBlue(int blue) {
        if (this.blue == blue)
            return;
        this.blue = blue;
        notifyPropertyChanged(BR.blue);
        if (leds_turned_on)
            updateColors();
    }

    @Bindable
    public int getBlue() {
        return blue;
    }

    public void setGreen(int green) {
        if (this.green == green)
            return;
        this.green = green;
        notifyPropertyChanged(BR.green);
        if (leds_turned_on)
            updateColors();
    }

    @Bindable
    public int getGreen() {
        return green;
    }

    public void setLedPin(String ledPin) {
        if (this.ledPin == ledPin)
            return;
        this.ledPin = ledPin;
    }

    @Bindable
    public String getLedPin() {
        return ledPin;
    }

    public void updatePins()
    {
        LedSetRequest r = new LedSetRequest();
        r.ledArrNum = Integer.parseInt(ledcount);
        r.ledArrPin = Integer.parseInt(ledPin);
        restController.getRestClient().setLedConfig(r,setLedConfigCallback);
    }

    private ApiServiceCallback<String> setLedConfigCallback = new ApiServiceCallback<String>() {
        @Override
        public void onResponse(String response) {

        }
    };
}
