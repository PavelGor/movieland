package com.gordeev.movieland.controller.util;

import com.gordeev.movieland.vo.Currency;

import java.beans.PropertyEditorSupport;

public class CurrencyConverter extends PropertyEditorSupport {
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(Currency.getByName(text));
    }
}
